package com.mishaismenska.hackatonrsschoolapp.data.staticPresets

enum class DrinkPresets(val percentage: Int, val typicalTares: List<VolumePresets>) {
    VODKA(40, listOf(
        VolumePresets.SHOT,
        VolumePresets.VODKA_GLASS
    )),
    JINN_TONIC(8, listOf(VolumePresets.VODKA_GLASS)),
    WINE_RED(11, listOf(VolumePresets.RED_WINE_GLASS)),
    WINE_WHITE(21, listOf(
        VolumePresets.RED_WINE_GLASS,
        VolumePresets.WHITE_WINE_GLASS,
        VolumePresets.VODKA_GLASS
    )),
    WINE_RED_CHEAP(12, listOf(VolumePresets.WHITE_WINE_GLASS)),
    WINE_WHITE_CHEAP(8, listOf(
        VolumePresets.RED_WINE_GLASS,
        VolumePresets.WHITE_WINE_GLASS,
        VolumePresets.VODKA_GLASS
    )),
    FRUIT_WINE_CHEAP(20, listOf(
        VolumePresets.RED_WINE_GLASS,
        VolumePresets.WHITE_WINE_GLASS,
        VolumePresets.VODKA_GLASS
    )),
    BEER_DARK(8, listOf(
        VolumePresets.PINT,
        VolumePresets.BEER_GLASS_LARGE,
        VolumePresets.BEER_GLASS
    )),
    BEER_LIGHT(5, listOf(
        VolumePresets.PINT,
        VolumePresets.BEER_GLASS_LARGE,
        VolumePresets.BEER_GLASS
    )),
    BEER_DARK_CHEAP(6, listOf(
        VolumePresets.PINT,
        VolumePresets.BEER_GLASS_LARGE,
        VolumePresets.BEER_GLASS
    )),
    BEER_LIGHT_CHEAP(4, listOf(
        VolumePresets.PINT,
        VolumePresets.BEER_GLASS_LARGE,
        VolumePresets.BEER_GLASS
    )),
    MOONSHINE(70, listOf(
        VolumePresets.SHOT,
        VolumePresets.VODKA_GLASS
    )),
    SPIRIT(99, listOf(
        VolumePresets.SHOT,
        VolumePresets.VODKA_GLASS
    )),
    WHISKEY_CHEAP(44, listOf(
        VolumePresets.SHOT,
        VolumePresets.VODKA_GLASS
    )),
    WHISKEY(40, listOf(VolumePresets.WHISKEY_GLASS)),
    LIQUOR(20, listOf(
        VolumePresets.SHOT,
        VolumePresets.VODKA_GLASS
    )),
    CHAMPAGNE(12, listOf(VolumePresets.WHITE_WINE_GLASS)),
    COGNAC(40, listOf(VolumePresets.COGNAC_GLASS))
}