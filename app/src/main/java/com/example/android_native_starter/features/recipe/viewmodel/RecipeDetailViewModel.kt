package com.example.android_native_starter.features.recipe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.recipe.data.model.Recipe
import com.example.android_native_starter.features.recipe.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipeDetailUiState(
    val detailResource: Resource<Recipe> = Resource.Loading()
)

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repo: RecipeRepository
) : ViewModel() {

    private val _detailUiState = MutableStateFlow(RecipeDetailUiState())
    val detailUiState: StateFlow<RecipeDetailUiState> = _detailUiState.asStateFlow()

    fun loadRecipeDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _detailUiState.update { it.copy(detailResource = Resource.Loading()) }
            val result = repo.getRecipeById(id)
            _detailUiState.update { it.copy(detailResource = result) }
        }
    }
}
