package com.example.android_native_starter.features.recipe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.recipe.data.model.Recipes
import com.example.android_native_starter.features.recipe.data.repository.RecipeRepository


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.example.android_native_starter.router.AppNavigator
import com.example.android_native_starter.features.recipe.RecipeDetailKey

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repo: RecipeRepository,
    private val appNavigator: AppNavigator
) : ViewModel() {

    fun onRecipeClicked(recipeId: String) {
        appNavigator.push(RecipeDetailKey(recipeId))
    }

    private val _recipeState = MutableLiveData<Resource<Recipes>>()
    val recipeState: LiveData<Resource<Recipes>> = _recipeState

    fun loadRecipes(limit: Int, skip: Int, sortBy: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Set loading state first
            _recipeState.postValue(Resource.Loading())
            
            // Make API call and post result
            val result = repo.getAllRecipes(limit, skip, sortBy)
            _recipeState.postValue(result)
        }
    }
}