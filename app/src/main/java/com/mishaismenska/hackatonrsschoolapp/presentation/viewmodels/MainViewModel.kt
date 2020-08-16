package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CheckIfConcentrationExceededUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetDrinksUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetStateUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetUserExistenceUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.AppNotificationManager
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkUIModel
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserStateUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.Behavior
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @Inject constructor(
    private val getDrinksUseCase: GetDrinksUseCase,
    private val getStateUseCase: GetStateUseCase,
    private val checkIfConcentrationExceededUseCase: CheckIfConcentrationExceededUseCase,
    private val appNotificationManager: AppNotificationManager,
    private val existenceUseCase: GetUserExistenceUseCase
) : ViewModel() {

    val userState = MutableLiveData<UserStateUIModel>()
    val isRecyclerVisible = MutableLiveData<Boolean>(false)
    val isEmptyRecyclerTextViewVisible = MutableLiveData(false)
    val isAddDrinkFabVisible = MutableLiveData<Boolean>(false)
    val drinks = MutableLiveData<List<DrinkUIModel>>()
    private var timer: Timer? = null

    fun getDrinks() {
        viewModelScope.launch(Dispatchers.IO) {
            val drinksFlow = getDrinksUseCase.getDrinks()
            var index = 0
            drinksFlow.collect {
                if (existenceUseCase.checkIfUserExists()) {
                    Log.d("Collect", this@MainViewModel.toString() + "collect number: " + index.toString())
                    if (index == 0) {
                        timer = Timer()
                        // TODO: move to constants
                        timer?.schedule(UpdateStateTimerTask(), 60000, 60000)
                    }
                    checkIfEmpty(it)
                    updateState(true)
                    drinks.postValue(it)
                    index++
                }
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
            checkIfConcentrationExceededUseCase.determineIfUserCanDrink(concentration)
        if (isAddDrinkFabVisible.value != newButtonState)
            isAddDrinkFabVisible.postValue(newButtonState)
    }

    private fun updateState(forceRecalculation: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val newState = getStateUseCase.getState(forceRecalculation) // crashes here
            userState.postValue(newState)
            determineButtonVisibility(newState.alcoholConcentration)
            if (forceRecalculation && newState.behavior != Behavior.SOBER) {
                appNotificationManager.scheduleBecameSoberNotification(newState.alcoholDepletionDuration)
            }
        }
    }

    override fun onCleared() {
        timer?.cancel()
        appNotificationManager.resetAllNotifications()
    }

    inner class UpdateStateTimerTask : TimerTask() {
        override fun run() {
            updateState(false)
        }
    }
}
