package com.mishaismenska.hackatonrsschoolapp.data.networking

import com.mishaismenska.hackatonrsschoolapp.data.models.DrinkDebugJsonDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserDebugJsonDataModel
import retrofit2.Response
import retrofit2.http.GET

interface DebugRetrofitService {
    @GET("/debug/gru")
    suspend fun getAllUsers(): Response<List<UserDebugJsonDataModel>>

    @GET("/debug/gd")
    suspend fun getAllDrinks(): Response<List<DrinkDebugJsonDataModel>>
}
