package com.mishaismenska.hackatonrsschoolapp.data

import android.content.Context
import com.mishaismenska.hackatonrsschoolapp.R
import com.mishaismenska.hackatonrsschoolapp.domain.interfaces.VolumeTitleRepository
import javax.inject.Inject

class VolumeTitleRepositoryImpl @Inject constructor(private val context: Context): VolumeTitleRepository {
    override fun provideVolumeTitles(): List<String> {
        return context.resources.getStringArray(R.array.volume_names).toList()
    }
}
