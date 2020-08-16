package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.content.Context
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.UnitConverter.mlToOz
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AddDrinkUseCase
import com.mishaismenska.hackatonrsschoolapp.presentation.models.DrinkSubmissionUIModel
import com.mishaismenska.hackatonrsschoolapp.staticPresets.DrinkPreset
import com.mishaismenska.hackatonrsschoolapp.staticPresets.VolumePreset
import java.time.LocalDateTime
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddDrinkViewModel @Inject constructor(
    private val addDrinkUseCase: AddDrinkUseCase,
    private val context: Context
) :
    ViewModel() {
    val formatter: MeasureFormat =
        MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.NARROW)
    val isFragmentOpened = MutableLiveData(true)
    // TODO: move to usecase
    private val isImperial = Locale.getDefault().country == "US"

    fun addDrink(binding: FragmentAddDrinkBinding) {
        val drinkType = parseDrinkType(binding, binding.typeInput.text.toString())
        val eaten = binding.eatenCheckbox.isChecked
        val volume = when (binding.volumeInput.text.toString()) {
            inlayMeasurement(binding, R.string.shot, VolumePreset.SHOT.volume) -> VolumePreset.SHOT
            inlayMeasurement(binding, R.string.vodka_glass, VolumePreset.VODKA_GLASS.volume) -> VolumePreset.VODKA_GLASS
            inlayMeasurement(binding, R.string.beer_glass, VolumePreset.BEER_GLASS.volume) -> VolumePreset.BEER_GLASS
            inlayMeasurement(binding, R.string.pint, VolumePreset.PINT.volume) -> VolumePreset.PINT
            inlayMeasurement(binding, R.string.beer_glass_large, VolumePreset.BEER_GLASS_LARGE.volume) -> VolumePreset.BEER_GLASS_LARGE
            inlayMeasurement(binding, R.string.red_wine_glass, VolumePreset.RED_WINE_GLASS.volume) -> VolumePreset.RED_WINE_GLASS
            inlayMeasurement(binding, R.string.white_wine_glass, VolumePreset.WHITE_WINE_GLASS.volume) -> VolumePreset.WHITE_WINE_GLASS
            inlayMeasurement(binding, R.string.cognac_glass, VolumePreset.COGNAC_GLASS.volume) -> VolumePreset.COGNAC_GLASS
            else -> VolumePreset.WHISKEY_GLASS
        }
        viewModelScope.launch(Dispatchers.IO) {
            addDrinkUseCase.addDrink(DrinkSubmissionUIModel(
                drinkType,
                LocalDateTime.now(),
                volume.volume,
                eaten
            ))
            isFragmentOpened.postValue(false)
        }
    }

    private fun inlayMeasurement(
        binding: FragmentAddDrinkBinding,
        id: Int,
        metricValue: Measure?
    ): String {
        return if (isImperial)
            binding.root.context.resources.getString(
                id,
                formatter.format(Measure(mlToOz(metricValue!!.number as Int), MeasureUnit.OUNCE))
            )
        else
            binding.root.context.resources.getString(id, formatter.format(metricValue))
    }

    fun parseDrinkType(binding: FragmentAddDrinkBinding, item: String): DrinkPreset {
        return when (item) {
            binding.typeInput.adapter.getItem(0) -> DrinkPreset.VODKA
            binding.typeInput.adapter.getItem(1) -> DrinkPreset.JINN_TONIC
            binding.typeInput.adapter.getItem(2) -> DrinkPreset.WINE_RED
            binding.typeInput.adapter.getItem(3) -> DrinkPreset.WINE_WHITE
            binding.typeInput.adapter.getItem(4) -> DrinkPreset.WINE_RED_CHEAP
            binding.typeInput.adapter.getItem(5) -> DrinkPreset.WINE_WHITE_CHEAP
            binding.typeInput.adapter.getItem(6) -> DrinkPreset.FRUIT_WINE_CHEAP
            binding.typeInput.adapter.getItem(7) -> DrinkPreset.BEER_DARK
            binding.typeInput.adapter.getItem(8) -> DrinkPreset.BEER_LIGHT
            binding.typeInput.adapter.getItem(9) -> DrinkPreset.BEER_DARK_CHEAP
            binding.typeInput.adapter.getItem(10) -> DrinkPreset.BEER_LIGHT_CHEAP
            binding.typeInput.adapter.getItem(11) -> DrinkPreset.MOONSHINE
            binding.typeInput.adapter.getItem(12) -> DrinkPreset.SPIRIT
            binding.typeInput.adapter.getItem(13) -> DrinkPreset.WHISKEY
            binding.typeInput.adapter.getItem(14) -> DrinkPreset.WHISKEY_CHEAP
            binding.typeInput.adapter.getItem(15) -> DrinkPreset.LIQUOR
            binding.typeInput.adapter.getItem(16) -> DrinkPreset.CHAMPAGNE
            else -> DrinkPreset.COGNAC
        }
    }

    override fun onCleared() {
        isFragmentOpened.postValue(true)
    }
}
