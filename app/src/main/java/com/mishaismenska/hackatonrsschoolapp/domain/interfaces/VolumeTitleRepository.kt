package com.mishaismenska.hackatonrsschoolapp.domain.interfaces

interface VolumeTitleRepository {
    fun provideVolumeTitles(): List<String>
}
