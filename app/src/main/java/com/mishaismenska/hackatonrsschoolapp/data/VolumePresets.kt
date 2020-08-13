package com.mishaismenska.hackatonrsschoolapp.data

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.mishaismenska.hackatonrsschoolapp.data.models.VolumePreset

val volumePreset =
    hashMapOf(
        VolumePreset.SHOT to Measure(40, MeasureUnit.MILLILITER),
        VolumePreset.VODKA_GLASS to Measure(250, MeasureUnit.MILLILITER),
        VolumePreset.BEER_GLASS to Measure(500, MeasureUnit.MILLILITER),
        VolumePreset.PINT to Measure(568, MeasureUnit.MILLILITER),
        VolumePreset.BEER_GLASS_LARGE to Measure(1000, MeasureUnit.MILLILITER),
        VolumePreset.RED_WINE_GLASS to Measure(160, MeasureUnit.MILLILITER),
        VolumePreset.WHITE_WINE_GLASS to Measure(120, MeasureUnit.MILLILITER),
        VolumePreset.COGNAC_GLASS to Measure(260, MeasureUnit.MILLILITER),
        VolumePreset.WHISKEY_GLASS to Measure(250, MeasureUnit.MILLILITER)
    )
