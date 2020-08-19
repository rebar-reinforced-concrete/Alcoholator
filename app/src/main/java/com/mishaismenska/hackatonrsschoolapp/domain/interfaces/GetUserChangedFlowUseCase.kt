package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import kotlinx.coroutines.flow.Flow

interface GetUserChangedFlowUseCase {
    suspend fun getUserChangedFlow(): Flow<Boolean>
}
