package com.example.android_native_starter.features.social.data

import com.example.android_native_starter.features.social.data.repository.IPostService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIPost {
    @Singleton
    @Provides
    fun providePostService(retrofit: Retrofit): IPostService {
        return retrofit.create(IPostService::class.java)
    }
}