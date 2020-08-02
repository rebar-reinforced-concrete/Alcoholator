package com.mishaismenska.hackatonrsschoolapp.viewmodels

import androidx.lifecycle.ViewModel
import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkType
import com.mishaismenska.hackatonrsschoolapp.data.models.Gender
import com.mishaismenska.hackatonrsschoolapp.data.models.VolumePreset
import com.mishaismenska.hackatonrsschoolapp.databinding.FragmentAddDrinkBinding
import javax.inject.Inject

class AddDrinkViewModel @Inject constructor() : ViewModel() {
    fun addDrink(binding: FragmentAddDrinkBinding) {
        val drinkType = when (binding.typeInput.text.toString()) {
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

        val volume = when (binding.volumeInput.text.toString()) {
            binding.volumeInput.adapter.getItem(0) -> VolumePreset.SHOT
            binding.volumeInput.adapter.getItem(1) -> VolumePreset.VODKA_GLASS
            binding.volumeInput.adapter.getItem(2) -> VolumePreset.BEER_GLASS
            binding.volumeInput.adapter.getItem(3) -> VolumePreset.PINT
            binding.volumeInput.adapter.getItem(4) -> VolumePreset.BEER_GLASS_LARGE
            binding.volumeInput.adapter.getItem(5) -> VolumePreset.RED_WINE_GLASS
            binding.volumeInput.adapter.getItem(6) -> VolumePreset.WHITE_WINE_GLASS
            binding.volumeInput.adapter.getItem(7) -> VolumePreset.COGNAC_GLASS
            else -> VolumePreset.WHISKEY_GLASS
        }
    }

    /*fun parseVolume(string: String): VolumePreset {
        when(string){
            binding.volumeInput.adapter.getItem(0) -> VolumePreset.SHOT
            binding.volumeInput.adapter.getItem(1) -> VolumePreset.VODKA_GLASS
            binding.volumeInput.adapter.getItem(2) -> VolumePreset.BEER_GLASS
            binding.volumeInput.adapter.getItem(3) -> VolumePreset.PINT
            binding.volumeInput.adapter.getItem(4) -> VolumePreset.BEER_GLASS_LARGE
            binding.volumeInput.adapter.getItem(5) -> VolumePreset.RED_WINE_GLASS
            binding.volumeInput.adapter.getItem(6) -> VolumePreset.WHITE_WINE_GLASS
            binding.volumeInput.adapter.getItem(7) -> VolumePreset.COGNAC_GLASS
            else -> VolumePreset.WHISKEY_GLASS
        }*/
}


