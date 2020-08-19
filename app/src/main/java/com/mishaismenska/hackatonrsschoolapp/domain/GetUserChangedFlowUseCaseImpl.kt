package com.mishaismenska.hackatonrsschoolapp.domain

import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetUserChangedFlowUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserChangedFlowUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) : GetUserChangedFlowUseCase {
    override suspend fun getUserChangedFlow(): Flow<Boolean> {
        return appDataRepository.getUser().map {
            true
        }
    }
}
