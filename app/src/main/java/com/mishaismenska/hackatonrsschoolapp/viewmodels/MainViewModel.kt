package com.mishaismenska.hackatonrsschoolapp.viewmodels

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.data.models.User
import com.mishaismenska.hackatonrsschoolapp.data.models.UserState
import com.mishaismenska.hackatonrsschoolapp.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.interfaces.CalculationManager
import com.mishaismenska.hackatonrsschoolapp.ui.DbResultsListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val context: Context,
    private val calculationManager: CalculationManager
) : ViewModel() {

    val userState = MutableLiveData<UserState>()
    val isAddDrinkFabVisible = MutableLiveData<Boolean>(false)
    private lateinit var user: User //TODO: make it LiveData

    fun getUser(dbResultsListener: DbResultsListener) = viewModelScope.launch(Dispatchers.IO) {
        user = appDataRepository.getUserWithDrinks()
        Handler(context.mainLooper).post {
            dbResultsListener.onUserLoaded(user)
            //createState() //doesn't work properly because of FUCKING DRINKS LIVEDATA
            Timer().schedule(UpdateStateTimerTask(), 0, 100)
        }
    }

    private fun determineButtonVisibility(concentration: Double){
        val newButtonState =
            calculationManager.determineIfUserStillCanDrink(concentration)
        if (isAddDrinkFabVisible.value != newButtonState)
            isAddDrinkFabVisible.postValue(newButtonState)
    }

    //TODO: make private
    fun createState(){
        if (user.drinks.value != null) {
            if (user.drinks.value.isNullOrEmpty()) isAddDrinkFabVisible.postValue(true)
            else {
                val newState = calculationManager.determineState(
                    user.age,
                    user.weight,
                    user.gender,
                    user.drinks.value!!
                )
                determineButtonVisibility(newState.alcoholConcentration)
                userState.postValue(newState)
            }
        }
    }

    private fun updateState(){
        if(userState.value != null){
            val newState = calculationManager.updateState(userState.value!!)
            determineButtonVisibility(newState.alcoholConcentration)
            userState.postValue(newState)
        }
    }

    //unnecessary here. Or not?
    inner class UpdateStateTimerTask : TimerTask() {
        override fun run() {
            updateState()
        }
    }
}
