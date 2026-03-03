package com.example.android_native_starter.features.quotes.data

import com.example.android_native_starter.features.quotes.data.repository.IQuoteServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIQuote {
    @Singleton
    @Provides
    fun providerQuoteService(retrofit: Retrofit) : IQuoteServices {
        return retrofit.create(IQuoteServices::class.java)
    }
}