package com.mishaismenska.hackatonrsschoolapp.viewmodels

import android.content.Context
import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.os.Handler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.mlToOz
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkType
import com.mishaismenska.hackatonrsschoolapp.data.models.VolumePreset
import com.mishaismenska.hackatonrsschoolapp.data.volumePreset
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import com.mishaismenska.hackatonrsschoolapp.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.ui.DbResultsListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class AddDrinkViewModel @Inject constructor(private val appDataRepository: AppDataRepository, private val context: Context) :
    ViewModel() {
    val formatter =
        MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.NARROW)
    val system = Locale.getDefault().country == "US"

    fun addDrink(binding: FragmentAddDrinkBinding, listener: DbResultsListener) {
        val drinkType = parseDrinkType(binding, binding.typeInput.text.toString())
        val eaten = binding.eatenCheckbox.isChecked
        val volume = when (binding.volumeInput.text.toString()) {
            inlayMeasurement(binding, R.string.shot, volumePreset[VolumePreset.SHOT]) -> VolumePreset.SHOT
            inlayMeasurement(binding, R.string.vodka_glass, volumePreset[VolumePreset.VODKA_GLASS]) -> VolumePreset.VODKA_GLASS
            inlayMeasurement(binding, R.string.beer_glass, volumePreset[VolumePreset.BEER_GLASS]) -> VolumePreset.BEER_GLASS
            inlayMeasurement(binding, R.string.pint, volumePreset[VolumePreset.PINT]) -> VolumePreset.PINT
            inlayMeasurement(binding, R.string.beer_glass_large, volumePreset[VolumePreset.BEER_GLASS_LARGE]) -> VolumePreset.BEER_GLASS_LARGE
            inlayMeasurement(binding, R.string.red_wine_glass, volumePreset[VolumePreset.RED_WINE_GLASS]) -> VolumePreset.RED_WINE_GLASS
            inlayMeasurement(binding, R.string.white_wine_glass, volumePreset[VolumePreset.WHITE_WINE_GLASS]) -> VolumePreset.WHITE_WINE_GLASS
            inlayMeasurement(binding, R.string.cognac_glass, volumePreset[VolumePreset.COGNAC_GLASS]) -> VolumePreset.COGNAC_GLASS
            else -> VolumePreset.WHISKEY_GLASS
        }
        viewModelScope.launch(Dispatchers.IO) {
            appDataRepository.addDrink(
                Drink(
                    drinkType,
                    LocalDateTime.now(),
                    volumePreset[volume]!!,
                    eaten
                )
            )
            Handler(context.mainLooper).post{
                listener.onDrinkAdded()
            }
        }
    }

    private fun inlayMeasurement(binding: FragmentAddDrinkBinding, id: Int, metricValue: Measure?): String {
        return if(system)
            binding.root.context.resources.getString(id, formatter.format(Measure(mlToOz(metricValue!!.number as Int), MeasureUnit.OUNCE)))
        else
            binding.root.context.resources.getString(id, formatter.format(metricValue))
    }

    fun parseDrinkType(binding: FragmentAddDrinkBinding, item: String): DrinkType {
        return when (item) {
            binding.typeInput.adapter.getItem(0) -> DrinkType.VODKA
            binding.typeInput.adapter.getItem(1) -> DrinkType.JINN_TONIC
            binding.typeInput.adapter.getItem(2) -> DrinkType.WINE_RED
            binding.typeInput.adapter.getItem(3) -> DrinkType.WINE_WHITE
            binding.typeInput.adapter.getItem(4) -> DrinkType.WINE_RED_CHEAP
            binding.typeInput.adapter.getItem(5) -> DrinkType.WINE_WHITE_CHEAP
            binding.typeInput.adapter.getItem(6) -> DrinkType.FRUIT_WINE_CHEAP
            binding.typeInput.adapter.getItem(7) -> DrinkType.BEER_DARK
            binding.typeInput.adapter.getItem(8) -> DrinkType.BEER_LIGHT
            binding.typeInput.adapter.getItem(9) -> DrinkType.BEER_DARK_CHEAP
            binding.typeInput.adapter.getItem(10) -> DrinkType.BEER_LIGHT_CHEAP
            binding.typeInput.adapter.getItem(11) -> DrinkType.MOONSHINE
            binding.typeInput.adapter.getItem(12) -> DrinkType.SPIRIT
            binding.typeInput.adapter.getItem(13) -> DrinkType.WHISKEY
            binding.typeInput.adapter.getItem(14) -> DrinkType.WHISKEY_CHEAP
            binding.typeInput.adapter.getItem(15) -> DrinkType.LIQUOR
            binding.typeInput.adapter.getItem(16) -> DrinkType.CHAMPAGNE
            else -> DrinkType.COGNAC
        }
    }


}
