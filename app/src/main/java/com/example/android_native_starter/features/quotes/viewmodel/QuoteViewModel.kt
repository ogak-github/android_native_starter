package com.example.android_native_starter.features.quotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.quotes.data.model.RandomQuotes
import com.example.android_native_starter.features.quotes.data.repository.QuoteRepository
import com.example.android_native_starter.router.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuoteUiState(
    val quoteResource: Resource<RandomQuotes> = Resource.Loading()
)

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repo: QuoteRepository,
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuoteUiState())
    val uiState: StateFlow<QuoteUiState> = _uiState.asStateFlow()

    fun onBackClicked() {
        appNavigator.pop()
    }

    fun getRandomQuote() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(quoteResource = Resource.Loading()) }
            val result = repo.getRandomQuote()
            _uiState.update { it.copy(quoteResource = result) }
        }
    }
}
