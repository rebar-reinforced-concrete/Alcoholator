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
import com.mishaismenska.hackatonrsschoolapp.data.staticPresets.DrinkPresets
import com.mishaismenska.hackatonrsschoolapp.data.staticPresets.VolumePresets
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import com.mishaismenska.hackatonrsschoolapp.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.ui.DbResultsListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class AddDrinkViewModel @Inject constructor(
    private val appDataRepository: AppDataRepository,
    private val context: Context
) :
    ViewModel() {
    val formatter =
        MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.NARROW)
    private val system = Locale.getDefault().country == "US"

    fun addDrink(binding: FragmentAddDrinkBinding, listener: DbResultsListener) {
        val drinkType = parseDrinkType(binding, binding.typeInput.text.toString())
        val eaten = binding.eatenCheckbox.isChecked
        val volume = when (binding.volumeInput.text.toString()) {
            inlayMeasurement(binding, R.string.shot, VolumePresets.SHOT.volume) -> VolumePresets.SHOT
            inlayMeasurement(binding, R.string.vodka_glass, VolumePresets.VODKA_GLASS.volume) -> VolumePresets.VODKA_GLASS
            inlayMeasurement(binding, R.string.beer_glass, VolumePresets.BEER_GLASS.volume) -> VolumePresets.BEER_GLASS
            inlayMeasurement(binding, R.string.pint, VolumePresets.PINT.volume) -> VolumePresets.PINT
            inlayMeasurement(binding, R.string.beer_glass_large, VolumePresets.BEER_GLASS_LARGE.volume) -> VolumePresets.BEER_GLASS_LARGE
            inlayMeasurement(binding, R.string.red_wine_glass, VolumePresets.RED_WINE_GLASS.volume) -> VolumePresets.RED_WINE_GLASS
            inlayMeasurement(binding, R.string.white_wine_glass, VolumePresets.WHITE_WINE_GLASS.volume) -> VolumePresets.WHITE_WINE_GLASS
            inlayMeasurement(binding, R.string.cognac_glass, VolumePresets.COGNAC_GLASS.volume) -> VolumePresets.COGNAC_GLASS
            else -> VolumePresets.WHISKEY_GLASS
        }
        viewModelScope.launch(Dispatchers.IO) {
            appDataRepository.addDrink(
                Drink(
                    drinkType,
                    LocalDateTime.now(),
                    volume.volume,
                    eaten
                )
            )
            Handler(context.mainLooper).post {
                listener.onDrinkAdded()
            }
        }
    }

    private fun inlayMeasurement(
        binding: FragmentAddDrinkBinding,
        id: Int,
        metricValue: Measure?
    ): String {
        return if (system)
            binding.root.context.resources.getString(
                id,
                formatter.format(Measure(mlToOz(metricValue!!.number as Int), MeasureUnit.OUNCE))
            )
        else
            binding.root.context.resources.getString(id, formatter.format(metricValue))
    }

    fun parseDrinkType(binding: FragmentAddDrinkBinding, item: String): DrinkPresets {
        return when (item) {
            binding.typeInput.adapter.getItem(0) -> DrinkPresets.VODKA
            binding.typeInput.adapter.getItem(1) -> DrinkPresets.JINN_TONIC
            binding.typeInput.adapter.getItem(2) -> DrinkPresets.WINE_RED
            binding.typeInput.adapter.getItem(3) -> DrinkPresets.WINE_WHITE
            binding.typeInput.adapter.getItem(4) -> DrinkPresets.WINE_RED_CHEAP
            binding.typeInput.adapter.getItem(5) -> DrinkPresets.WINE_WHITE_CHEAP
            binding.typeInput.adapter.getItem(6) -> DrinkPresets.FRUIT_WINE_CHEAP
            binding.typeInput.adapter.getItem(7) -> DrinkPresets.BEER_DARK
            binding.typeInput.adapter.getItem(8) -> DrinkPresets.BEER_LIGHT
            binding.typeInput.adapter.getItem(9) -> DrinkPresets.BEER_DARK_CHEAP
            binding.typeInput.adapter.getItem(10) -> DrinkPresets.BEER_LIGHT_CHEAP
            binding.typeInput.adapter.getItem(11) -> DrinkPresets.MOONSHINE
            binding.typeInput.adapter.getItem(12) -> DrinkPresets.SPIRIT
            binding.typeInput.adapter.getItem(13) -> DrinkPresets.WHISKEY
            binding.typeInput.adapter.getItem(14) -> DrinkPresets.WHISKEY_CHEAP
            binding.typeInput.adapter.getItem(15) -> DrinkPresets.LIQUOR
            binding.typeInput.adapter.getItem(16) -> DrinkPresets.CHAMPAGNE
            else -> DrinkPresets.COGNAC
        }
    }


}
