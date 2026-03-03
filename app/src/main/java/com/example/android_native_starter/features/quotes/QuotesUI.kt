package com.example.android_native_starter.features.quotes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.quotes.data.model.RandomQuotes
import com.example.android_native_starter.features.quotes.viewmodel.QuoteViewModel
import com.example.android_native_starter.features.recipe.EmptyView
import com.example.android_native_starter.features.recipe.ErrorView
import com.example.android_native_starter.features.recipe.LoadingView
import kotlin.collections.emptyList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesUI() {
    val viewModel: QuoteViewModel = hiltViewModel()
    val randomQuoteState by viewModel.randomQuoteState.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getRandomQuote()
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Quotes") },
            navigationIcon = {
                IconButton(onClick = {
                    viewModel.onBackClicked()
                }) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                }
            }
        )
    }) {
        it

        when (val state = randomQuoteState) {
            is Resource.Loading -> {
                LoadingView()
            }

            is Resource.Success -> {
                val quote = state.data?.quote
                val author = state.data?.author
                if (quote.isNullOrEmpty() && author.isNullOrEmpty()) {
                    EmptyView()
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "“$quote”",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "- $author",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontStyle = FontStyle.Italic
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }


                }
            }
            is Resource.Error -> {
                ErrorView(
                    message = state.message ?: "Unknown error occurred",
                    onRetry = {
                        viewModel.getRandomQuote()
                    }
                )
            }

            null -> {
                // Initial state - show loading
                EmptyView()
            }
        }
    }
}