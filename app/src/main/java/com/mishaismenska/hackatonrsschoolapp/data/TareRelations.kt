package com.mishaismenska.hackatonrsschoolapp.data

import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkType
import com.mishaismenska.hackatonrsschoolapp.data.models.VolumePreset

val TareRelations = hashMapOf(
    DrinkType.VODKA to listOf(VolumePreset.SHOT, VolumePreset.VODKA_GLASS),
    DrinkType.JINN_TONIC to listOf(VolumePreset.VODKA_GLASS),
    DrinkType.WINE_RED to listOf(VolumePreset.RED_WINE_GLASS),
    DrinkType.WINE_READ_CHEAP to listOf(
        VolumePreset.RED_WINE_GLASS,
        VolumePreset.WHITE_WINE_GLASS,
        VolumePreset.VODKA_GLASS
    ),
    DrinkType.WINE_WHITE to listOf(VolumePreset.WHITE_WINE_GLASS),
    DrinkType.WINE_WHITE_CHEAP to listOf(
        VolumePreset.RED_WINE_GLASS,
        VolumePreset.WHITE_WINE_GLASS,
        VolumePreset.VODKA_GLASS
    ),
    DrinkType.FRUIT_WINE_CHEAP to listOf(
        VolumePreset.RED_WINE_GLASS,
        VolumePreset.WHITE_WINE_GLASS,
        VolumePreset.VODKA_GLASS
    ),
    DrinkType.BEER_DARK to listOf(
        VolumePreset.PINT,
        VolumePreset.BEER_GLASS_LARGE,
        VolumePreset.BEER_GLASS
    ),
    DrinkType.BEER_LIGHT_CHEAP to listOf(
        VolumePreset.PINT,
        VolumePreset.BEER_GLASS_LARGE,
        VolumePreset.BEER_GLASS
    ),
    DrinkType.BEER_LIGHT to listOf(
        VolumePreset.PINT,
        VolumePreset.BEER_GLASS_LARGE,
        VolumePreset.BEER_GLASS
    ),
    DrinkType.BEER_DARK_CHEAP to listOf(
        VolumePreset.PINT,
        VolumePreset.BEER_GLASS_LARGE,
        VolumePreset.BEER_GLASS
    ),
    DrinkType.MOONSHINE to listOf(VolumePreset.SHOT, VolumePreset.VODKA_GLASS),
    DrinkType.SPIRIT to listOf(VolumePreset.SHOT, VolumePreset.VODKA_GLASS),
    DrinkType.WHISKEY_CHEAP to listOf(VolumePreset.SHOT, VolumePreset.VODKA_GLASS),
    DrinkType.WHISKEY to listOf(VolumePreset.WHISKEY_GLASS),
    DrinkType.LIQUOR to listOf(VolumePreset.SHOT, VolumePreset.VODKA_GLASS),
    DrinkType.CHAMPAGNE to listOf(VolumePreset.WHITE_WINE_GLASS),
    DrinkType.COGNAC to listOf(VolumePreset.COGNAC_GLASS)
)