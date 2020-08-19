package com.mishaismenska.hackatonrsschoolapp.data

import android.content.Context
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GendersRepository
import javax.inject.Inject

class GendersRepositoryImpl @Inject constructor(private val context: Context) : GendersRepository {
    override fun provideGenders(): Array<String> {
        return context.resources.getStringArray(R.array.genders_names)
    }
}
