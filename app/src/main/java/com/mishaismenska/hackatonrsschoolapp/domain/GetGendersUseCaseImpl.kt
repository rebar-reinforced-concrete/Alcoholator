package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GendersRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetGendersUseCase
import javax.inject.Inject

class GetGendersUseCaseImpl @Inject constructor(private val gendersRepository: GendersRepository) : GetGendersUseCase {
    override fun getGenders(): Array<String> = gendersRepository.provideGenders()
}
