package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

interface SignInUseCase {
    fun signIn(): GoogleSignInOptions
}
