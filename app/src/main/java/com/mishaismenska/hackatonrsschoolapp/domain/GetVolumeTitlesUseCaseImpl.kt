package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetVolumeTitlesUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.VolumeTitleRepository
import javax.inject.Inject

class GetVolumeTitlesUseCaseImpl @Inject constructor(private val volumeTitleRepository: VolumeTitleRepository) : GetVolumeTitlesUseCase {
    override fun getVolumeTitles(): List<String> {
        return volumeTitleRepository.provideVolumeTitles()
    }
}
