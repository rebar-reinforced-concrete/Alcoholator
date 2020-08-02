package com.mishaismenska.hackatonrsschoolapp.ui


import com.mishaismenska.hackatonrsschoolapp.data.models.User

interface DbResultsListener {
    fun onUserLoaded(user: User) {}
    fun onUserAdded() {}
}
