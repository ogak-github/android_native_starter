package com.example.android_native_starter.features.recipe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.recipe.RecipeDetailKey
import com.example.android_native_starter.features.recipe.data.model.Recipe
import com.example.android_native_starter.features.recipe.data.model.Recipes
import com.example.android_native_starter.features.recipe.data.repository.RecipeRepository
import com.example.android_native_starter.features.recipe.data.repository.Sort
import com.example.android_native_starter.router.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

data class RecipeUiState(
    val recipeResource: Resource<Recipes> = Resource.Loading()
)

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repo: RecipeRepository,
    private val appNavigator: AppNavigator
) : ViewModel() {

    private var currentSkip = 0
    private val limit = 10
    private var isLastPage = false
    
    private var _sortBy = Sort.NAME.name.lowercase()
    var sortBy = _sortBy
    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    init {
        loadRecipes(sortBy = _sortBy, isNextPage = false)
    }

    fun loadRecipes(sortBy: String, isNextPage: Boolean) {
        // Guard: Jangan load jika sudah di halaman terakhir (hanya untuk next page)
        // Atau jika sedang loading more
        if ((isNextPage && isLastPage) || (isNextPage && _uiState.value.recipeResource is Resource.Loading)) return

        if (isNextPage) {
            // Hanya tambah skip jika pemanggilan sebelumnya BUKAN error
            // Jika sebelumnya error, berarti kita sedang retry skip yang sama
            if (_uiState.value.recipeResource !is Resource.Error) {
                currentSkip += limit
            }
        } else {
            currentSkip = 0
            isLastPage = false
        }

        // Tampilkan loading state hanya jika bukan load more (agar data lama tetap terlihat)
        if (!isNextPage) {
            _uiState.update { it.copy(recipeResource = Resource.Loading()) }
        }

        viewModelScope.launch(Dispatchers.IO) {
            // Simpan recipes lama jika kita sedang load more
            val oldRecipes = if (isNextPage) _uiState.value.recipeResource.data?.recipes ?: emptyList() else emptyList()
            
            val result = repo.getAllRecipes(limit = limit, skip = currentSkip, sortBy = sortBy)

            _uiState.update { currentState ->
                when (result) {
                    is Resource.Success -> {
                        val responseData = result.data
                        val newRecipes = responseData?.recipes ?: emptyList()

                        // Update status halaman terakhir
                        if (currentSkip + newRecipes.size >= (responseData?.total ?: 0)) {
                            isLastPage = true
                        }

                        val combinedRecipes = oldRecipes + newRecipes

                        currentState.copy(
                            recipeResource = Resource.Success(
                                responseData?.copy(recipes = combinedRecipes) ?: Recipes(
                                    recipes = combinedRecipes,
                                    total = responseData?.total ?: combinedRecipes.size,
                                    skip = currentSkip,
                                    limit = limit
                                )
                            )
                        )
                    }
                    is Resource.Error -> {
                        // Jika error saat load more, kita tetap simpan data lama di dalam Resource.Error
                        currentState.copy(
                            recipeResource = Resource.Error(
                                result.message ?: "Unknown Error",
                                data = if (isNextPage) currentState.recipeResource.data else null
                            )
                        )
                    }
                    else -> currentState
                }
            }
        }
    }

    fun onRecipeClicked(recipeId: Int) {
        appNavigator.navigateTo(RecipeDetailKey(recipeId))
    }

    fun onBackClicked() {
        appNavigator.pop()
    }
}
