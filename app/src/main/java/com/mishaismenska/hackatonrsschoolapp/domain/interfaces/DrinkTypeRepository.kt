package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface DrinkTypeRepository {
    fun provideDrinks(): List<String>
}
