package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetVolumePresetsUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.MeasureSystemsManager
import com.mishaismenska.hackatonrsschoolapp.staticPresets.VolumePreset
import javax.inject.Inject

class GetVolumePresetsUseCaseImpl @Inject constructor(private val measureSystemsManager: MeasureSystemsManager): GetVolumePresetsUseCase{
    override fun getVolumePresets(): List<Double> {
        return VolumePreset.values().map{
            measureSystemsManager.convertVolumeToImperialIfRequired(it.volume.number.toInt())
        }
    }
}
