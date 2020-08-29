package com.mishaismenska.hackatonrsschoolapp.presentation.viewmodels

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.AppDataRepository
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GetServerExistenceUseCase
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val serverExistenceUseCase: GetServerExistenceUseCase,
    private val appDataRepository: AppDataRepository
) :
    ViewModel() {

    private val _userExistsOnServer = MutableLiveData<Boolean>()
    val userExistsOnServer: LiveData<Boolean>
        get() = _userExistsOnServer

    fun signIn(activity: Activity): Intent { //fixme?: activity i think its janky, wdyt?
        val gso = signInUseCase.signIn()
        val googleSignInClient = GoogleSignIn.getClient(activity, gso)
        return googleSignInClient.signInIntent
    }


    fun handleSignInResult(@NonNull completedTask: Task<GoogleSignInAccount>?) {
        try {
            val account = completedTask!!.getResult(ApiException::class.java)
            val idToken = account!!.idToken.toString()
            viewModelScope.launch(Dispatchers.IO) {
                val exists = serverExistenceUseCase.checkIfUserExists(idToken)
                _userExistsOnServer.postValue(exists)
            }
        } catch (e: ApiException) {
            Log.w("google login failed", "signInResult:failed code=" + e.statusCode)
        }

    }

    fun syncUserData() {

    }
}
