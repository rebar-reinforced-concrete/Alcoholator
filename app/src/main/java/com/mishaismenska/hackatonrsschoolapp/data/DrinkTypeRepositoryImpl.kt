package com.mishaismenska.hackatonrsschoolapp.data

import android.content.Context
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.DrinkTypeRepository
import javax.inject.Inject

class DrinkTypeRepositoryImpl @Inject constructor(private val context: Context) : DrinkTypeRepository {
    override fun provideDrinks(): List<String> {
        return context.resources.getStringArray(R.array.drink_types).toList()
    }
}
