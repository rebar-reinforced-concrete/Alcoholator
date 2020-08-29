package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.domain.models.GoogleIdTokenDomainModel

interface GetGoogleIdTokenUseCase {
    suspend fun getToken(): GoogleIdTokenDomainModel
}
