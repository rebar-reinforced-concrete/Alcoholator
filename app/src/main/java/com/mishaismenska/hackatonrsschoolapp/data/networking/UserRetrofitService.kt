package com.mishaismenska.hackatonrsschoolapp.data.networking

import com.mishaismenska.hackatonrsschoolapp.data.models.UserAlterGenderJsonDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserAlterNameJsonDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserAlterWeightJsonDataModel
import com.mishaismenska.hackatonrsschoolapp.data.models.UserJsonDataModel
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserRetrofitService {
    @POST("/users/adduser")
    suspend fun addUser(@Body user: UserJsonDataModel)

    @PATCH("/users/alterusername")
    suspend fun alterUserName(@Body userJson: UserAlterNameJsonDataModel)

    @PATCH("/users/alteruserweight")
    suspend fun alterUserWeight(@Body user: UserAlterWeightJsonDataModel)

    @PATCH("/users/alterusergender")
    suspend fun alterUserGender(@Body user: UserAlterGenderJsonDataModel)
}
