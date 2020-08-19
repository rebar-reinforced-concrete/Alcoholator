package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CheckIfConcentrationExceededUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetDrinksUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetStateUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetUserChangedFlowUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.AppNotificationManager
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkUIModel
import com.mishaismenska.hackatonrsschoolapp.presentation.models.UserStateUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants
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
    private val getUserChangedFlowUseCase: GetUserChangedFlowUseCase
) : ViewModel() {

    private val _userState = MutableLiveData<UserStateUIModel>()
    val userState: LiveData<UserStateUIModel>
        get() = _userState

    private val _isRecyclerVisible = MutableLiveData<Boolean>(false)
    val isRecyclerVisible: LiveData<Boolean>
        get() = _isRecyclerVisible

    private val _isEmptyRecyclerTextViewVisible = MutableLiveData(false)
    val isEmptyRecyclerTextViewVisible: LiveData<Boolean>
        get() = _isEmptyRecyclerTextViewVisible

    private val _isAddDrinkFabVisible = MutableLiveData<Boolean>(false)
    val isAddDrinkFabVisible: LiveData<Boolean>
        get() = _isAddDrinkFabVisible

    private val _drinks = MutableLiveData<List<DrinkUIModel>>()
    val drinks: LiveData<List<DrinkUIModel>>
        get() = _drinks

    private var timer: Timer? = null

    fun getDrinksAndUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val drinksFlow = getDrinksUseCase.getDrinks()
            var index = 0
            drinksFlow.collect {
                if (index == 0) {
                    timer = Timer()
                    timer?.schedule(UpdateStateTimerTask(), AppConstants.UPDATE_UI_TIMER_PERIOD, AppConstants.UPDATE_UI_TIMER_PERIOD)
                }
                checkIfEmpty(it)
                updateState(true)
                _drinks.postValue(it)
                index++
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            getUserChangedFlowUseCase.getUserChangedFlow().collect {
                updateState(true)
            }
        }
    }

    private fun checkIfEmpty(data: List<DrinkUIModel>?) {
        return if (data.isNullOrEmpty()) {
            _isRecyclerVisible.postValue(false)
            _isEmptyRecyclerTextViewVisible.postValue(true)
        } else {
            _isRecyclerVisible.postValue(true)
            _isEmptyRecyclerTextViewVisible.postValue(false)
        }
    }

    private fun determineButtonVisibility(concentration: Double) {
        val newButtonState =
            checkIfConcentrationExceededUseCase.determineIfUserCanDrink(concentration)
        if (isAddDrinkFabVisible.value != newButtonState)
            _isAddDrinkFabVisible.postValue(newButtonState)
    }

    private fun updateState(forceRecalculation: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val newState = getStateUseCase.getState(forceRecalculation)
            if (newState != null) {
                _userState.postValue(newState)
                determineButtonVisibility(newState.alcoholConcentration)
                if (forceRecalculation && newState.behavior != Behavior.SOBER) {
                    appNotificationManager.scheduleBecameSoberNotification(newState.alcoholDepletionDuration)
                }
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
