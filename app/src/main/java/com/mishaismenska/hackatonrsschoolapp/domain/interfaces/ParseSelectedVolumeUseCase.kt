
package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import android.icu.util.Measure

interface ParseSelectedVolumeUseCase {
    fun parseVolume(drinkVolumeSelection: String): Measure
}
