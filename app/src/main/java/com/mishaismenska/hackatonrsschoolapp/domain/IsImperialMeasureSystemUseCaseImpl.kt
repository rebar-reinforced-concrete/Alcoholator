package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.IsImperialMeasureSystemUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.MeasureSystemsManager
import javax.inject.Inject

class IsImperialMeasureSystemUseCaseImpl
    @Inject constructor(private val measureSystemsManager: MeasureSystemsManager) : IsImperialMeasureSystemUseCase {
    override fun checkIfMeasureSystemImperial(): Boolean = measureSystemsManager.checkIfMeasureSystemImperial()
}
