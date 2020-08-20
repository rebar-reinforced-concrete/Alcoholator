package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.*
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkSubmissionUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.VolumePreset
import java.time.LocalDateTime
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddDrinkViewModel @Inject constructor(
    private val addDrinkUseCase: AddDrinkUseCase,
    private val calculateIndexesUseCase: CalculateIndexesUseCase,
    private val measureSystemsManager: MeasureSystemsManager,
    private val parseSelectedVolumeUseCase: ParseSelectedVolumeUseCase,
    private val getVolumeTitlesUseCase: GetVolumeTitlesUseCase
) :
    ViewModel() {
    val formatter: MeasureFormat =
        MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.NARROW)
    val isFragmentOpened = MutableLiveData(true)

    fun addDrink(eaten: Boolean, volume: String, type: String) {
        val volumeValue = parseSelectedVolumeUseCase.parseVolume(volume)
        viewModelScope.launch(Dispatchers.IO) {
            addDrinkUseCase.addDrink(
                DrinkSubmissionUIModel(
                    type,
                    LocalDateTime.now(),
                    volumeValue,
                    eaten
                )
            )
            isFragmentOpened.postValue(false)
        }
    }

    private fun getFormattedWeight(weight: Double): String {
        val measureUnit = if (measureSystemsManager.checkIfMeasureSystemImperial()) MeasureUnit.OUNCE else MeasureUnit.MILLILITER
        return formatter.format(Measure(weight, measureUnit))
    }

    fun getVolumeStrings(): MutableList<String> =
        getVolumeTitlesUseCase.getVolumeTitles().mapIndexed { index, s ->
            s.format(
                getFormattedWeight(measureSystemsManager.convertVolumeToImperialIfRequired(VolumePreset.values()[index].volume.number.toInt())) //FIXME: here we go again. do we want to ditch Measures?!
            )
        }.toMutableList()

    override fun onCleared() {
        isFragmentOpened.postValue(true)
    }

    fun calculateIndexes(item: String): List<Int> {
        return calculateIndexesUseCase.calculateIndexes(item)
    }
}
