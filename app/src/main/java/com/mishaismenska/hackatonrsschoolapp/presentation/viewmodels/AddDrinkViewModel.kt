package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.content.Context
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.location.LocationManager
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddDrinkUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CalculateIndexesUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetCurrentLocationUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetVolumePresetsUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetVolumeTitlesUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.MeasureSystemsManager
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.ParseSelectedVolumeUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.AddDrinkFragment
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.AlertDialogManager
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkSubmissionUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Locale
import javax.inject.Inject

class AddDrinkViewModel @Inject constructor(
    private val addDrinkUseCase: AddDrinkUseCase,
    private val calculateIndexesUseCase: CalculateIndexesUseCase,
    private val measureSystemsManager: MeasureSystemsManager,
    private val parseSelectedVolumeUseCase: ParseSelectedVolumeUseCase,
    private val getVolumeTitlesUseCase: GetVolumeTitlesUseCase,
    private val getVolumePresetsUseCase: GetVolumePresetsUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val alertDialogManager: AlertDialogManager
) :
    ViewModel() {
    private val formatter: MeasureFormat =
        MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.NARROW)
    val isFragmentOpened = MutableLiveData(true)

    private var explanationsTriggered = 0

    fun addDrink(eaten: Boolean, volume: String, type: String, locationManager: LocationManager) {
        val volumeValue = parseSelectedVolumeUseCase.parseVolume(volume)
        val location = getCurrentLocationUseCase.getCurrentLocation(locationManager)
        viewModelScope.launch(Dispatchers.IO) {
            addDrinkUseCase.addDrink(
                DrinkSubmissionUIModel(
                    type,
                    LocalDateTime.now(),
                    volumeValue,
                    eaten,
                    location
                )
            )
            isFragmentOpened.postValue(false)
        }
    }

    fun requestGPSServices(context: Context) = alertDialogManager.showEnableGPSServicesDialog(context)
    fun triggerExplanation(context: Context, event: (fragment: Fragment) -> Unit, fragment: AddDrinkFragment) {
        explanationsTriggered++
        if (explanationsTriggered == 2) finishAffinity(fragment.requireActivity()) // maemezs
        alertDialogManager.showPermissionsExplanation(context, event, fragment)
    }

    private fun getFormattedWeight(weight: Double): String {
        val measureUnit = if (measureSystemsManager.checkIfMeasureSystemImperial()) MeasureUnit.OUNCE else MeasureUnit.MILLILITER
        return formatter.format(Measure(weight, measureUnit))
    }

    fun getVolumeStrings(): MutableList<String> {
        val volumes = getVolumePresetsUseCase.getVolumePresets()
        return getVolumeTitlesUseCase.getVolumeTitles().mapIndexed { index, s ->
            s.format(
                getFormattedWeight(volumes[index])
            )
        }.toMutableList()
    }

    override fun onCleared() {
        isFragmentOpened.postValue(true)
    }

    fun calculateIndexes(item: String): List<Int> {
        return calculateIndexesUseCase.calculateIndexes(item)
    }
}
