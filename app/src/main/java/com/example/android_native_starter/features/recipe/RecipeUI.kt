package com.example.android_native_starter.features.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.android_native_starter.core.ui.components.EmptyView
import com.example.android_native_starter.core.ui.components.ErrorView
import com.example.android_native_starter.core.ui.components.LoadingView
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.recipe.data.model.Recipe
import com.example.android_native_starter.features.recipe.data.repository.Sort
import com.example.android_native_starter.features.recipe.viewmodel.RecipeUiState
import com.example.android_native_starter.features.recipe.viewmodel.RecipeViewModel

@Composable
fun RecipeView(
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadRecipes(10, 0, Sort.NAME.name.lowercase())
    }

    RecipeScreen(
        uiState = uiState,
        onBackClick = viewModel::onBackClicked,
        onRecipeClick = viewModel::onRecipeClicked,
        onRetry = { viewModel.loadRecipes(10, 0, Sort.NAME.name.lowercase()) },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    uiState: RecipeUiState,
    onBackClick: () -> Unit,
    onRecipeClick: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Recipe") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (val state = uiState.recipeResource) {
                is Resource.Loading -> LoadingView()
                is Resource.Success -> {
                    val recipes = state.data?.recipes ?: emptyList()
                    if (recipes.isEmpty()) {
                        EmptyView()
                    } else {
                        RecipeList(
                            recipes = recipes,
                            onRecipeClick = onRecipeClick
                        )
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
fun RecipeList(
    recipes: List<Recipe>,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        items(recipes, key = { it.id }) { recipe ->
            RecipeItem(
                name = recipe.name,
                onClick = { onRecipeClick(recipe.id) }
            )
        }
    }
}

@Composable
fun RecipeItem(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(16.dp)
        )
    }
}
