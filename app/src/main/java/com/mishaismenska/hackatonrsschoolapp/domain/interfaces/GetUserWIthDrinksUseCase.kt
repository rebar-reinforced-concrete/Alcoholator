package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

import com.mishaismenska.hackatonrsschoolapp.domain.models.UserWithDrinksDomainModel

interface GetUserWIthDrinksUseCase {
    suspend fun getUserWithDrinks(): UserWithDrinksDomainModel
}