package com.example.android_native_starter.features.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.recipe.data.model.Recipe
import com.example.android_native_starter.features.recipe.data.repository.Sort
import com.example.android_native_starter.features.recipe.viewmodel.RecipeViewModel

@Composable
fun RecipeView() {
    val viewModel: RecipeViewModel = hiltViewModel()
    val recipeState by viewModel.recipeState.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.loadRecipes(10, 0, Sort.NAME.name.lowercase())
    }

    when (val state = recipeState) {
        is Resource.Loading -> {
            LoadingView()
        }
        is Resource.Success -> {
            val recipes = state.data?.recipes ?: emptyList()
            if (recipes.isEmpty()) {
                EmptyView()
            } else {
                RecipeList(recipes) { recipeId ->
                    viewModel.onRecipeClicked(recipeId.toInt())
                }
            }
        }
        is Resource.Error -> {
            ErrorView(
                message = state.message ?: "Unknown error occurred",
                onRetry = {
                    viewModel.loadRecipes(10, 0, Sort.NAME.name.lowercase())
                }
            )
        }
        null -> {
            // Initial state - show loading
            EmptyView()
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No recipes found",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Oops! Something went wrong",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun RecipeList(recipes: List<Recipe>, onRecipeClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        items(recipes.size) { index ->
            RecipeItem(recipes[index].name, onClick = { onRecipeClick(recipes[index].id) })
        }
    }
}

@Composable
fun RecipeItem(name: String, onClick: () -> Unit) {
    androidx.compose.material3.Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(16.dp)
        )
    }
}