package com.mishaismenska.hackatonrsschoolapp.staticPresets

enum class DrinkPreset(val percentage: Int, val typicalTares: List<VolumePreset>) {
    VODKA(40, listOf(
        VolumePreset.SHOT,
        VolumePreset.VODKA_GLASS
    )),
    JINN_TONIC(8, listOf(VolumePreset.VODKA_GLASS)),
    WINE_RED(11, listOf(VolumePreset.RED_WINE_GLASS)),
    WINE_WHITE(21, listOf(
        VolumePreset.RED_WINE_GLASS,
        VolumePreset.WHITE_WINE_GLASS,
        VolumePreset.VODKA_GLASS
    )),
    WINE_RED_CHEAP(12, listOf(VolumePreset.WHITE_WINE_GLASS)),
    WINE_WHITE_CHEAP(8, listOf(
        VolumePreset.RED_WINE_GLASS,
        VolumePreset.WHITE_WINE_GLASS,
        VolumePreset.VODKA_GLASS
    )),
    FRUIT_WINE_CHEAP(20, listOf(
        VolumePreset.RED_WINE_GLASS,
        VolumePreset.WHITE_WINE_GLASS,
        VolumePreset.VODKA_GLASS
    )),
    BEER_DARK(8, listOf(
        VolumePreset.PINT,
        VolumePreset.BEER_GLASS_LARGE,
        VolumePreset.BEER_GLASS
    )),
    BEER_LIGHT(5, listOf(
        VolumePreset.PINT,
        VolumePreset.BEER_GLASS_LARGE,
        VolumePreset.BEER_GLASS
    )),
    BEER_DARK_CHEAP(6, listOf(
        VolumePreset.PINT,
        VolumePreset.BEER_GLASS_LARGE,
        VolumePreset.BEER_GLASS
    )),
    BEER_LIGHT_CHEAP(4, listOf(
        VolumePreset.PINT,
        VolumePreset.BEER_GLASS_LARGE,
        VolumePreset.BEER_GLASS
    )),
    MOONSHINE(70, listOf(
        VolumePreset.SHOT,
        VolumePreset.VODKA_GLASS
    )),
    SPIRIT(99, listOf(
        VolumePreset.SHOT,
        VolumePreset.VODKA_GLASS
    )),
    WHISKEY_CHEAP(44, listOf(
        VolumePreset.SHOT,
        VolumePreset.VODKA_GLASS
    )),
    WHISKEY(40, listOf(VolumePreset.WHISKEY_GLASS)),
    LIQUOR(20, listOf(
        VolumePreset.SHOT,
        VolumePreset.VODKA_GLASS
    )),
    CHAMPAGNE(12, listOf(VolumePreset.WHITE_WINE_GLASS)),
    COGNAC(40, listOf(VolumePreset.COGNAC_GLASS))
}
