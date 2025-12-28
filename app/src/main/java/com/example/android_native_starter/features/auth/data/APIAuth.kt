package com.example.android_native_starter.features.auth.data

import com.example.android_native_starter.core.userdata.IAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIAuth {

    @Singleton
    @Provides
    fun providerAuthService(retrofit: Retrofit): IAuthService {
        return retrofit.create(IAuthService::class.java)
    }
}