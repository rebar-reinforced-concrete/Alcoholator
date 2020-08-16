package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.DetermineMaximalAlcoholConcentrationExceededUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetDrinksUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetStateUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.AppNotificationManager
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkUIModel
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserStateUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Behaviours
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getDrinksUseCase: GetDrinksUseCase,
    private val getStateUseCase: GetStateUseCase,
    private val determineMaximalAlcoholConcentrationExceededUseCase: DetermineMaximalAlcoholConcentrationExceededUseCase,
    private val appNotificationManager: AppNotificationManager
) : ViewModel() {

    val userState = MutableLiveData<UserStateUIModel>()
    val isRecyclerVisible = MutableLiveData<Boolean>(false)
    val isEmptyRecyclerTextViewVisible = MutableLiveData(false)
    val isAddDrinkFabVisible = MutableLiveData<Boolean>(false)
    val drinks = MutableLiveData<List<DrinkUIModel>>()

    fun getDrinks() {
        viewModelScope.launch(Dispatchers.IO) {
            val drinksFlow = getDrinksUseCase.getDrinks()
            var index = 0
            drinksFlow.collect {
                if(index == 0){
                    Timer().schedule(UpdateStateTimerTask(), 60000, 60000)
                }
                checkIfEmpty(it)
                updateState(true)
                drinks.postValue(it)
                index++
            }
        }
    }

    private fun checkIfEmpty(data: List<DrinkUIModel>?) {
        return if (data.isNullOrEmpty()) {
            isRecyclerVisible.postValue(false)
            isEmptyRecyclerTextViewVisible.postValue(true)
        } else {
            isRecyclerVisible.postValue(true)
            isEmptyRecyclerTextViewVisible.postValue(false)
        }
    }

    private fun determineButtonVisibility(concentration: Double) {
        val newButtonState =
            determineMaximalAlcoholConcentrationExceededUseCase.determineIfUserStillCanDrink(concentration)
        if (isAddDrinkFabVisible.value != newButtonState)
            isAddDrinkFabVisible.postValue(newButtonState)
    }

    private fun updateState(forceRecalculation: Boolean) {
        viewModelScope.launch {
            val newState = getStateUseCase.getState(forceRecalculation)
            userState.postValue(newState)
            determineButtonVisibility(newState.alcoholConcentration)
            if(forceRecalculation && newState.behaviour != Behaviours.SOBER){
                appNotificationManager.scheduleSoberNotification(newState.soberTime)
            }
        }
    }

    inner class UpdateStateTimerTask : TimerTask() {
        override fun run() {
            updateState(false)
        }
    }
}
