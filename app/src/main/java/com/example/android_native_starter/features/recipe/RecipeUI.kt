package com.example.android_native_starter.features.recipe

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.withCompositionLocals
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import coil3.compose.AsyncImage
import com.example.android_native_starter.core.ui.components.EmptyView
import com.example.android_native_starter.core.ui.components.ErrorView
import com.example.android_native_starter.core.ui.components.LoadingView
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.recipe.data.model.Recipe
import com.example.android_native_starter.features.recipe.data.model.Recipes
import com.example.android_native_starter.features.recipe.viewmodel.RecipeUiState
import com.example.android_native_starter.features.recipe.viewmodel.RecipeListViewModel
import com.example.android_native_starter.router.LocalSharedTransitionScope

@Composable
fun RecipeView(
    modifier: Modifier = Modifier,
    viewModel: RecipeListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RecipeScreen(
        uiState = uiState,
        onBackClick = viewModel::onBackClicked,
        onRecipeClick = viewModel::onRecipeClicked,
        onRetry = { viewModel.loadRecipes(viewModel.sortBy, isNextPage = true) },
        onLoadMore = { viewModel.loadRecipes(viewModel.sortBy, isNextPage = true) },
        modifier = modifier,

    )
}

@Preview(showSystemUi = true)
@Composable
fun RecipeScreenPreview() {
    var recipes: Recipes = Recipes(
        total = 10,
        skip = 0,
        limit = 1,
        recipes = listOf(
            Recipe(
                id = 1,
                name = "Classic Margherita Pizza",
                ingredients = listOf(
                    "Pizza dough",
                    "Tomato sauce",
                    "Fresh mozzarella cheese",
                    "Fresh basil leaves",
                    "Olive oil",
                    "Salt and pepper to taste"
                ),
                instructions = listOf(
                    "Preheat the oven to 475°F (245°C).",
                    "Roll out the pizza dough and spread tomato sauce evenly.",
                    "Top with slices of fresh mozzarella and fresh basil leaves.",
                    "Drizzle with olive oil and season with salt and pepper.",
                    "Bake in the preheated oven for 12-15 minutes or until the crust is golden brown.",
                    "Slice and serve hot."
                ),
                prepTimeMinutes = 30,
                cookTimeMinutes = 15,
                servings = 5,
                difficulty = "easy",
                cuisine = "Italy",
                caloriesPerServing = 300.0,
                tags = listOf(
                    "Pizza",
                    "Italian"
                ),
                userId = 45,
                image = "https://cdn.dummyjson.com/recipe-images/1.webp",
                mealType = listOf<String>(
                    "Dinner"
                ),
                rating = 4.3,
                reviewCount = 1000
            )
        )
    )
                



    RecipeScreen(
        uiState = RecipeUiState(
            recipeResource = Resource.Success(recipes)
        ),
        onBackClick = {},
        onRecipeClick = {},
        onRetry = { },
        onLoadMore = { },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    uiState: RecipeUiState,
    onBackClick: () -> Unit,
    onRecipeClick: (Int) -> Unit,
    onRetry: () -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
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
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            when (val state = uiState.recipeResource) {
                is Resource.Loading -> LoadingView()
                is Resource.Success -> {
                    val recipes = state.data?.recipes ?: emptyList()
                    if (recipes.isEmpty()) {
                        EmptyView()
                    } else {
                        RecipeList(
                            recipes = recipes,
                            onRecipeClick = onRecipeClick,
                            onLoadMore = onLoadMore
                        )
                    }
                }
                is Resource.Error -> {
                    // Jika ada data lama (saat load more error), tetap tampilkan list tapi mungkin dengan snackbar error
                    // Tapi untuk saat ini kita ikuti logic awal: tampilkan ErrorView jika ada error
                    val recipes = state.data?.recipes ?: emptyList()
                    if (recipes.isNotEmpty()) {
                         RecipeList(
                            recipes = recipes,
                            onRecipeClick = onRecipeClick,
                            onLoadMore = onLoadMore
                        )
                        // TODO: Tampilkan snackbar error
                    } else {
                        ErrorView(
                            message = state.message ?: "Unknown error occurred",
                            onRetry = onRetry
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeList(
    recipes: List<Recipe>,
    onRecipeClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index >= recipes.size - 2
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onLoadMore()
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        items(recipes, key = { it.id }) { recipe ->
            RecipeItem(
                recipe = recipe,
                onClick = { onRecipeClick(recipe.id) }
            )
        }
    }
}

@Composable
fun RecipeItem(
    recipe: Recipe,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxSize()
    ) {

        Card(
            shape = CardDefaults.elevatedShape,
            onClick = onClick,
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                Modifier.padding(8.dp)
            ) {

                RecipeItemImage(recipeImage = recipe.image, recipeDescription = recipe.name)
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = recipe.name,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = recipe.cuisine,
                        modifier = Modifier.padding(8.dp)
                    )
                }


            }
        }

    }
}

@Composable
fun RecipeItemImage(
    recipeImage: String,
    recipeDescription: String?
) {
    val sharedScope = LocalSharedTransitionScope.current
    val animatedScope = LocalNavAnimatedContentScope.current

    if(sharedScope != null) {
        with(sharedScope) {
            AsyncImage(
                model = recipeImage,
                contentDescription = recipeDescription,
                modifier = Modifier
                    .size(84.dp)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = recipeDescription + recipeImage),
                        animatedVisibilityScope = animatedScope,
                    )
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
                clipToBounds = true
            )
        }
    } else {
        AsyncImage(
            model = recipeImage,
            contentDescription = recipeDescription,
            modifier = Modifier
                .size(84.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
    }
}