package com.mishaismenska.hackatonrsschoolapp.di

import com.mishaismenska.hackatonrsschoolapp.data.networking.DebugRetrofitService
import com.mishaismenska.hackatonrsschoolapp.data.networking.DrinksRetrofitService
import com.mishaismenska.hackatonrsschoolapp.data.networking.UserRetrofitService
import com.mishaismenska.hackatonrsschoolapp.staticPresets.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(AppConstants.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideDebugApiService(retrofit: Retrofit): DebugRetrofitService {
        return retrofit.create(DebugRetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRetrofitService(retrofit: Retrofit): UserRetrofitService {
        return retrofit.create(UserRetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun provideDrinksRetrofitService(retrofit: Retrofit): DrinksRetrofitService {
        return retrofit.create(DrinksRetrofitService::class.java)
    }
}
