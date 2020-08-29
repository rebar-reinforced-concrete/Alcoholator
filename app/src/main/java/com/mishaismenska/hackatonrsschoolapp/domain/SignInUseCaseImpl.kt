package com.mishaismenska.hackatonrsschoolapp.domain

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.SignInUseCase
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(private val context: Context) : SignInUseCase {
    override fun signIn(): GoogleSignInOptions {
        GoogleSignIn.getLastSignedInAccount(context)
        return GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestIdToken(context.getString(R.string.googleAppKey))
            .requestEmail()
            .build()
    }
}
