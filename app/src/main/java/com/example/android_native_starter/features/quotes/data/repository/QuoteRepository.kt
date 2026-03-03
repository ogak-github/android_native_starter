package com.example.android_native_starter.features.quotes.data.repository

import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.quotes.data.model.RandomQuotes
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class QuoteRepository @Inject constructor(private val quoteService: IQuoteServices) {
    suspend fun getRandomQuote(): Resource<RandomQuotes> {
        return try {
            val result = quoteService.randomQuote()
            Resource.Success(result)
        } catch (e: HttpException) {
            Resource.Error("Server error: ${e.code()} - ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("No internet connection. Please check your network.")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.localizedMessage ?: "Unknown error"}")
        }
    }
}