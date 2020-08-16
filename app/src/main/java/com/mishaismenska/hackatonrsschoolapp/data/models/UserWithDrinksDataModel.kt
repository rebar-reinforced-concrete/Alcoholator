package com.mishaismenska.hackatonrsschoolapp.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class UserWithDrinksDataModel (
    @Embedded val user: UserDataModel,
    @Relation(parentColumn = "userId", entityColumn = "consumedBy")
    val drinks: List<DrinkDataModel>
)