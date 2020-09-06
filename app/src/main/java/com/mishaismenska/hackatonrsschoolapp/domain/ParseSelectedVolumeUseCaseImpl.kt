package com.mishaismenska.hackatonrsschoolapp.domain

import android.icu.util.Measure
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.ParseSelectedVolumeUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.VolumeTitleRepository
import com.mishaismenska.hackatonrsschoolapp.staticPresets.VolumePreset
import javax.inject.Inject

class ParseSelectedVolumeUseCaseImpl @Inject constructor(private val VolumeTitleRepository: VolumeTitleRepository) : ParseSelectedVolumeUseCase {
    override fun parseVolume(drinkVolumeSelection: String): Measure {
        val volumes = VolumeTitleRepository.provideVolumeTitles()
        for ((index, value) in volumes.withIndex()) {
            val significantNamePart = value.split(" (")[0]
            if (drinkVolumeSelection.contains(significantNamePart)) return VolumePreset.values()[index].volume
        }
        return VolumePreset.VODKA_GLASS.volume
    }
}
