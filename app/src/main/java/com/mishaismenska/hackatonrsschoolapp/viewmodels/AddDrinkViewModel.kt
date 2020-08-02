package com.mishaismenska.hackatonrsschoolapp.viewmodels

import android.content.Context
import android.icu.util.Measure
import android.os.Handler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.data.models.Drink
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkType
import com.mishaismenska.hackatonrsschoolapp.data.models.Gender
import com.mishaismenska.hackatonrsschoolapp.data.models.VolumePreset
import com.mishaismenska.hackatonrsschoolapp.data.volumePreset
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import com.mishaismenska.hackatonrsschoolapp.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.ui.DbResultsListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

class AddDrinkViewModel @Inject constructor(private val appDataRepository: AppDataRepository, private val context: Context) :
    ViewModel() {
    fun addDrink(binding: FragmentAddDrinkBinding, listener: DbResultsListener) {
        val drinkType = parseDrinkType(binding, binding.typeInput.text.toString())

        val volume = when (binding.volumeInput.text.toString()) {
            binding.root.context.resources.getString(R.string.shot) -> VolumePreset.SHOT
            binding.root.context.resources.getString(R.string.vodka_glass) -> VolumePreset.VODKA_GLASS
            binding.root.context.resources.getString(R.string.beer_glass) -> VolumePreset.BEER_GLASS
            binding.root.context.resources.getString(R.string.pint) -> VolumePreset.PINT
            binding.root.context.resources.getString(R.string.beer_glass_large) -> VolumePreset.BEER_GLASS_LARGE
            binding.root.context.resources.getString(R.string.red_wine_glass) -> VolumePreset.RED_WINE_GLASS
            binding.root.context.resources.getString(R.string.white_wine_glass) -> VolumePreset.WHITE_WINE_GLASS
            binding.root.context.resources.getString(R.string.cognac_glass) -> VolumePreset.COGNAC_GLASS
            else -> VolumePreset.WHISKEY_GLASS
        }
        viewModelScope.launch(Dispatchers.IO) {
            appDataRepository.addDrink(
                Drink(
                    drinkType,
                    LocalDateTime.now(),
                    volumePreset[volume]!!
                )
            )
            Handler(context.mainLooper).post{
                listener.onDrinkAdded()
            }
        }
    }
    fun parseDrinkType(binding: FragmentAddDrinkBinding, item: String): DrinkType {
        return when (item) {
            binding.typeInput.adapter.getItem(0) -> DrinkType.VODKA
            binding.typeInput.adapter.getItem(1) -> DrinkType.JINN_TONIC
            binding.typeInput.adapter.getItem(2) -> DrinkType.WINE_RED
            binding.typeInput.adapter.getItem(3) -> DrinkType.WINE_WHITE
            binding.typeInput.adapter.getItem(4) -> DrinkType.WINE_READ_CHEAP
            binding.typeInput.adapter.getItem(5) -> DrinkType.WINE_WHITE_CHEAP
            binding.typeInput.adapter.getItem(6) -> DrinkType.FRUIT_WINE_CHEAP
            binding.typeInput.adapter.getItem(7) -> DrinkType.BEER_DARK
            binding.typeInput.adapter.getItem(8) -> DrinkType.BEER_LIGHT
            binding.typeInput.adapter.getItem(9) -> DrinkType.BEER_DARK_CHEAP
            binding.typeInput.adapter.getItem(10) -> DrinkType.BEER_LIGHT_CHEAP
            binding.typeInput.adapter.getItem(11) -> DrinkType.MOONSHINE
            binding.typeInput.adapter.getItem(11) -> DrinkType.SPIRIT
            binding.typeInput.adapter.getItem(13) -> DrinkType.WHISKEY
            binding.typeInput.adapter.getItem(14) -> DrinkType.WHISKEY_CHEAP
            binding.typeInput.adapter.getItem(15) -> DrinkType.LIQUOR
            binding.typeInput.adapter.getItem(16) -> DrinkType.CHAMPAGNE
            else -> DrinkType.COGNAC
        }
    }


}

