package com.example.android_native_starter.features.quotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.quotes.data.model.RandomQuotes
import com.example.android_native_starter.features.quotes.data.repository.QuoteRepository
import com.example.android_native_starter.router.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repo: QuoteRepository,
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val _randomQuoteState = MutableLiveData<Resource<RandomQuotes>>()
    val randomQuoteState: LiveData<Resource<RandomQuotes>> = _randomQuoteState

    fun onBackClicked() {
        appNavigator.pop()
    }

    fun getRandomQuote() {
        viewModelScope.launch(Dispatchers.IO) {
            // Set loading state first
            _randomQuoteState.postValue(Resource.Loading())

            // Make API call and post result
            val result = repo.getRandomQuote()
            _randomQuoteState.postValue(result)
        }
    }
}