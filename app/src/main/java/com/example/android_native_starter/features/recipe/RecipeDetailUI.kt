package com.example.android_native_starter.features.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.android_native_starter.router.AppNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.recipe.viewmodel.RecipeViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeDetailKey(val recipeId: Int) : NavKey, Parcelable

@Module
@InstallIn(ActivityRetainedComponent::class)
object RecipeDetailModule {
    @IntoSet
    @Provides
    fun provideRecipeDetailEntryBuilder(appNavigator: AppNavigator): EntryProviderScope<NavKey>.() -> Unit = {
        recipeDetailEntryBuilder(appNavigator)
    }
}

fun EntryProviderScope<NavKey>.recipeDetailEntryBuilder(appNavigator: AppNavigator) {
    entry<RecipeDetailKey> { key ->
        RecipeDetailUI(
            recipeId = key.recipeId,
            onBackClick = { appNavigator.pop() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailUI(recipeId: Int, onBackClick: () -> Unit) {
    val viewModel: RecipeViewModel = hiltViewModel()
    val recipeDetailState by viewModel.recipeDetailState.observeAsState()

    LaunchedEffect(recipeId) {
        viewModel.loadRecipeDetail(recipeId)
    }

    Scaffold(
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
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {

            when(val state = recipeDetailState) {
                is Resource.Loading -> {
                    LoadingView()
                }

                is Resource.Error -> {
                    ErrorView(message = state.message ?: "Unknown error occurred", onRetry = {
                        viewModel.loadRecipeDetail(recipeId.toInt())
                    })

                }

                is Resource.Success -> {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Details for recipe:")
                        Text("ID: $recipeId")
                        Text("Recipe Name: ${recipeDetailState?.data?.name}")
                        Text("Instructions: ${recipeDetailState?.data?.instructions}")
                    }

                }

                null -> {
                    EmptyView()
                }
            }

        }
    }
}
