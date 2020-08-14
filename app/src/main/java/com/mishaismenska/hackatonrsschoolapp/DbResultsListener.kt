package com.mishaismenska.hackatonrsschoolapp

interface DbResultsListener {
    fun onUserLoaded() {}
    fun onUserAdded() {}
    fun onDrinkAdded() {}
}
