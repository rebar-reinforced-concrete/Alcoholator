package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.icu.text.MeasureFormat
import android.icu.util.Measure
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddDrinkUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.CalculateIndexesUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.ConvertIfRequiredUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkSubmissionUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.VolumePreset
import java.time.LocalDateTime
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddDrinkViewModel @Inject constructor(
    private val addDrinkUseCase: AddDrinkUseCase,
    private val convertIfRequiredUseCase: ConvertIfRequiredUseCase,
    private val calculateIndexesUseCase: CalculateIndexesUseCase
) :
    ViewModel() {
    val formatter: MeasureFormat =
        MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.NARROW)
    val isFragmentOpened = MutableLiveData(true)

    fun addDrink(binding: FragmentAddDrinkBinding) {
        val eaten = binding.eatenCheckbox.isChecked
        // PLEASE. NOT HERE. NOT FUCKING HERE
        val volume = when (binding.volumeInput.text.toString()) {
            inlayMeasurement(binding, R.string.shot, VolumePreset.SHOT.volume) -> VolumePreset.SHOT
            inlayMeasurement(
                binding,
                R.string.vodka_glass,
                VolumePreset.VODKA_GLASS.volume
            ) -> VolumePreset.VODKA_GLASS
            inlayMeasurement(
                binding,
                R.string.beer_glass,
                VolumePreset.BEER_GLASS.volume
            ) -> VolumePreset.BEER_GLASS
            inlayMeasurement(binding, R.string.pint, VolumePreset.PINT.volume) -> VolumePreset.PINT
            inlayMeasurement(
                binding,
                R.string.beer_glass_large,
                VolumePreset.BEER_GLASS_LARGE.volume
            ) -> VolumePreset.BEER_GLASS_LARGE
            inlayMeasurement(
                binding,
                R.string.red_wine_glass,
                VolumePreset.RED_WINE_GLASS.volume
            ) -> VolumePreset.RED_WINE_GLASS
            inlayMeasurement(
                binding,
                R.string.white_wine_glass,
                VolumePreset.WHITE_WINE_GLASS.volume
            ) -> VolumePreset.WHITE_WINE_GLASS
            inlayMeasurement(
                binding,
                R.string.cognac_glass,
                VolumePreset.COGNAC_GLASS.volume
            ) -> VolumePreset.COGNAC_GLASS
            else -> VolumePreset.WHISKEY_GLASS
        }
        viewModelScope.launch(Dispatchers.IO) {
            addDrinkUseCase.addDrink(
                DrinkSubmissionUIModel(
                    binding.typeInput.text.toString(),
                    LocalDateTime.now(),
                    volume.volume,
                    eaten
                )
            )
            isFragmentOpened.postValue(false)
        }
    }

    // HID THIS STUFF SOMEWHERE
    private fun inlayMeasurement(
        binding: FragmentAddDrinkBinding,
        id: Int,
        metricValue: Measure?
    ): String {
        return binding.root.context.resources.getString(
            id,
            formatter.format(convertIfRequiredUseCase.convertToImperialIfRequired(metricValue!!))
        )
    }

    override fun onCleared() {
        isFragmentOpened.postValue(true)
    }

    fun convertMeasureIfRequired(volume: VolumePreset): Measure {
        return convertIfRequiredUseCase.convertToImperialIfRequired(volume.volume)
    }

    fun calculateIndexes(item: String): List<Int> {
        return calculateIndexesUseCase.calculateIndexes(item)
    }
}
