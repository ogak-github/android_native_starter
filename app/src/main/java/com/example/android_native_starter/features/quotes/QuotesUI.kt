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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.android_native_starter.core.ui.components.EmptyView
import com.example.android_native_starter.core.ui.components.ErrorView
import com.example.android_native_starter.core.ui.components.LoadingView
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.quotes.viewmodel.QuoteUiState
import com.example.android_native_starter.features.quotes.viewmodel.QuoteViewModel

@Composable
fun QuotesUI(
    modifier: Modifier = Modifier,
    viewModel: QuoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getRandomQuote()
    }

    QuotesScreen(
        uiState = uiState,
        onBackClick = viewModel::onBackClicked,
        onRetry = viewModel::getRandomQuote,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesScreen(
    uiState: QuoteUiState,
    onBackClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Quotes") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (val state = uiState.quoteResource) {
                is Resource.Loading -> LoadingView()
                is Resource.Success -> {
                    val quote = state.data?.quote
                    val author = state.data?.author
                    if (quote.isNullOrEmpty() && author.isNullOrEmpty()) {
                        EmptyView()
                    } else {
                        QuoteContent(quote = quote ?: "", author = author ?: "")
                    }
                }
                is Resource.Error -> {
                    ErrorView(
                        message = state.message ?: "Unknown error occurred",
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}

@Composable
fun QuoteContent(
    quote: String,
    author: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
