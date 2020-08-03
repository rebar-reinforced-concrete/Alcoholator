package com.mishaismenska.hackatonrsschoolapp.viewmodels

import android.content.Context
import android.os.Handler
import android.util.Log
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
    private lateinit var user: User

    fun getUser(dbResultsListener: DbResultsListener) = viewModelScope.launch(Dispatchers.IO) {
        user = appDataRepository.getUserWithDrinks()
        Handler(context.mainLooper).post {
            dbResultsListener.onUserLoaded(user)
            val timer = Timer()
            timer.schedule(UpdateStateTimerTask(), 0, 60000)
        }
    }

    fun updateState(){
        if (user.drinks.value != null) {
            userState.postValue(
                calculationManager.determineState(
                    user.age,
                    user.weight,
                    user.gender,
                    user.drinks.value!!
                )
            )
        }
    }

    inner class UpdateStateTimerTask : TimerTask() {
        override fun run() {
            updateState()
        }

    }
}
