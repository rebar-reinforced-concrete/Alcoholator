package com.mishaismenska.hackatonrsschoolapp.staticPresets

import android.icu.util.Measure
import android.icu.util.MeasureUnit

enum class VolumePresets(val volume: Measure) {
    SHOT(Measure(40, MeasureUnit.MILLILITER)),
    VODKA_GLASS(Measure(250, MeasureUnit.MILLILITER)),
    BEER_GLASS(Measure(500, MeasureUnit.MILLILITER)),
    PINT(Measure(568, MeasureUnit.MILLILITER)),
    BEER_GLASS_LARGE(Measure(1000, MeasureUnit.MILLILITER)),
    RED_WINE_GLASS(Measure(160, MeasureUnit.MILLILITER)),
    WHITE_WINE_GLASS(Measure(120, MeasureUnit.MILLILITER)),
    COGNAC_GLASS(Measure(260, MeasureUnit.MILLILITER)),
    WHISKEY_GLASS(Measure(250, MeasureUnit.MILLILITER))
}
