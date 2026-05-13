package com.example.android_native_starter.features.recipe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.recipe.RecipeDetailKey
import com.example.android_native_starter.features.recipe.data.model.Recipe
import com.example.android_native_starter.features.recipe.data.model.Recipes
import com.example.android_native_starter.features.recipe.data.repository.RecipeRepository
import com.example.android_native_starter.router.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipeUiState(
    val recipeResource: Resource<Recipes> = Resource.Loading()
)

data class RecipeDetailUiState(
    val detailResource: Resource<Recipe> = Resource.Loading()
)

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repo: RecipeRepository,
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    private val _detailUiState = MutableStateFlow(RecipeDetailUiState())
    val detailUiState: StateFlow<RecipeDetailUiState> = _detailUiState.asStateFlow()

    fun onRecipeClicked(recipeId: Int) {
        appNavigator.navigateTo(RecipeDetailKey(recipeId))
    }

    fun onBackClicked() {
        appNavigator.pop()
    }

    fun loadRecipes(limit: Int, skip: Int, sortBy: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(recipeResource = Resource.Loading()) }
            val result = repo.getAllRecipes(limit, skip, sortBy)
            _uiState.update { it.copy(recipeResource = result) }
        }
    }

    fun loadRecipeDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _detailUiState.update { it.copy(detailResource = Resource.Loading()) }
            val result = repo.getRecipeById(id)
            _detailUiState.update { it.copy(detailResource = result) }
        }
    }
}
