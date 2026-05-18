package com.example.android_native_starter.features.recipe

import android.os.Parcelable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import coil3.compose.AsyncImage
import com.example.android_native_starter.core.ui.components.EmptyView
import com.example.android_native_starter.core.ui.components.ErrorView
import com.example.android_native_starter.core.ui.components.LoadingView
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.recipe.viewmodel.RecipeDetailUiState
import com.example.android_native_starter.features.recipe.viewmodel.RecipeDetailViewModel
import com.example.android_native_starter.router.AppNavigator
import com.example.android_native_starter.router.EntryBuilder
import com.example.android_native_starter.router.LocalSharedTransitionScope
import com.example.android_native_starter.router.verticalSlideMetadata
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeDetailKey(val recipeId: Int) : NavKey, Parcelable

@Module
@InstallIn(SingletonComponent::class)
object RecipeDetailModule {
    @IntoSet
    @Provides
    fun provideRecipeDetailEntryBuilder(appNavigator: AppNavigator): @JvmSuppressWildcards EntryBuilder = {
        recipeDetailEntryBuilder(appNavigator)
    }
}

fun EntryProviderScope<NavKey>.recipeDetailEntryBuilder(appNavigator: AppNavigator) {
    entry<RecipeDetailKey>(
        // override GLOBAL animation in Nav3.kt
        metadata = verticalSlideMetadata,
    ) { key ->
        RecipeDetailRoute(
            recipeId = key.recipeId,
            onBackClick = { appNavigator.pop() }
        )
    }
}

@Composable
fun RecipeDetailRoute(
    recipeId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.detailUiState.collectAsStateWithLifecycle()

    LaunchedEffect(recipeId) {
        viewModel.loadRecipeDetail(recipeId)
    }

    RecipeDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onRetry = { viewModel.loadRecipeDetail(recipeId) },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    uiState: RecipeDetailUiState,
    onBackClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Recipe Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {
            when (val state = uiState.detailResource) {
                is Resource.Loading -> LoadingView()
                is Resource.Error -> {
                    ErrorView(
                        message = state.message ?: "Unknown error occurred",
                        onRetry = onRetry
                    )
                }
                is Resource.Success -> {
                    val recipe = state.data
                    if (recipe == null) {
                        EmptyView()
                    } else {
                        Column(modifier = Modifier.padding(16.dp)) {
                            RecipeImageCard(
                                recipeImage = recipe.image,
                                recipeContentDescription = recipe.name
                            )
                            Text("Details for recipe:")
                            Text("Recipe Name: ${recipe.name}")
                            Text("Instructions: ${recipe.instructions}")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RecipeImageCard(recipeImage: String, recipeContentDescription: String?) {
    val sharedScope = LocalSharedTransitionScope.current
    val animatedScope = LocalNavAnimatedContentScope.current


    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        Box {
            if(sharedScope != null) {
                with(sharedScope) {
                    AsyncImage(
                        model = recipeImage,
                        contentDescription = recipeContentDescription,
                        modifier = Modifier
                            .fillMaxSize()

                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = recipeContentDescription + recipeImage),
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
                    contentDescription = recipeContentDescription,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.large),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }

}