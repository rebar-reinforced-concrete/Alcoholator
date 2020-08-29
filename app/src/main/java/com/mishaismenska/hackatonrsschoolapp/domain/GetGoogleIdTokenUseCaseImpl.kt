package com.mishaismenska.hackatonrsschoolapp.domain

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetGoogleIdTokenUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.models.GoogleIdTokenDomainModel
import javax.inject.Inject


class GetGoogleIdTokenUseCaseImpl @Inject constructor(private val context: Context) : GetGoogleIdTokenUseCase {
    override suspend fun getToken(): GoogleIdTokenDomainModel {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return GoogleIdTokenDomainModel(account!!.idToken!!)
    }
}
