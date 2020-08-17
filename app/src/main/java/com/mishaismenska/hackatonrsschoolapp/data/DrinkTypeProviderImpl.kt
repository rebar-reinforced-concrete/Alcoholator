package com.mishaismenska.hackatonrsschoolapp.data

import android.content.Context
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.DrinkTypeProvider
import javax.inject.Inject

class DrinkTypeProviderImpl @Inject constructor(private val context: Context) : DrinkTypeProvider {
    override fun provideDrinks(): List<String> {
        return context.resources.getStringArray(R.array.drink_types).toList()
    }
}