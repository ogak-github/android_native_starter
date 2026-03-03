package com.example.android_native_starter.features.quotes.data.repository

import com.example.android_native_starter.features.quotes.data.model.RandomQuotes
import retrofit2.http.GET
import retrofit2.http.Query

interface IQuoteServices {
    @GET("quotes/random")
    suspend fun randomQuote(): RandomQuotes
}