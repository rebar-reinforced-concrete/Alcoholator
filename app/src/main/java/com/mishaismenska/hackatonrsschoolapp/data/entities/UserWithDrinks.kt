package com.mishaismenska.hackatonrsschoolapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithDrinks(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "consumedBy"
    ) val drinks: List<DrinkEntity>
)