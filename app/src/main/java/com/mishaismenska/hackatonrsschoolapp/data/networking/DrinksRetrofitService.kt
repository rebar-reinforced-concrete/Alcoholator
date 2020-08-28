package com.mishaismenska.hackatonrsschoolapp.data.networking

import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkAddDrinkJsonDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserWithDrinksJsonDataModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DrinksRetrofitService {
    @GET("/drinks/getuserwithdrinks")
    suspend fun getUserWithDrinks(@Query("googleId") id: String): UserWithDrinksJsonDataModel

    @POST("/drinks/adddrink")
    suspend fun addDrink(@Body drinkAddDrinkJsonDataModel: DrinkAddDrinkJsonDataModel)

    @POST("/drinks/removedrink")
    suspend fun removeDrink(@Body drinkAddDrinkJsonDataModel: DrinkAddDrinkJsonDataModel)
}
