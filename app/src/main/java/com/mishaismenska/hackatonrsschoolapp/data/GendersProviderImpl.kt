package com.mishaismenska.hackatonrsschoolapp.data

import android.content.Context
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.GendersProvider
import javax.inject.Inject

class GendersProviderImpl @Inject constructor(private val context: Context) : GendersProvider{
    override fun provideGenders(): Array<String> {
        return context.resources.getStringArray(R.array.genders_names)
    }
}
