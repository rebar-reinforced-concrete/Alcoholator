package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.content.Context
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.DetermineMaximalAlcoholConcentrationExceededUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetDrinksUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetStateUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkUIModel
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserStateUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getDrinksUseCase: GetDrinksUseCase,
    private val getStateUseCase: GetStateUseCase,
    private val determineMaximalAlcoholConcentrationExceededUseCase: DetermineMaximalAlcoholConcentrationExceededUseCase,
    private val context: Context
) : ViewModel() {

    val userState = MutableLiveData<UserStateUIModel>()
    val isAddDrinkFabVisible = MutableLiveData<Boolean>(false)
    val drinks = MutableLiveData<List<DrinkUIModel>>()

    fun getDrinks() {
        viewModelScope.launch(Dispatchers.IO) {
            //TODO: unsubscribe
            val drinksFromUseCase = getDrinksUseCase.getDrinks()
            Handler(context.mainLooper).post {
                drinksFromUseCase.observeForever {
                    drinks.postValue(it)
                    Timer().schedule(UpdateStateTimerTask(), 0, 60000)
                }

            }
        }
    }

    private fun determineButtonVisibility(concentration: Double) {
        val newButtonState =
            determineMaximalAlcoholConcentrationExceededUseCase.determineIfUserStillCanDrink(
                concentration
            )
        if (isAddDrinkFabVisible.value != newButtonState)
            isAddDrinkFabVisible.postValue(newButtonState)
    }

    private fun updateState() {
        if (userState.value != null) {
            viewModelScope.launch {
                val newState = getStateUseCase.getState()
                userState.postValue(newState)
                determineButtonVisibility(newState.alcoholConcentration)
                userState.postValue(newState)
            }
        }
    }

    inner class UpdateStateTimerTask : TimerTask() {
        override fun run() {
            updateState()
        }
    }
}
