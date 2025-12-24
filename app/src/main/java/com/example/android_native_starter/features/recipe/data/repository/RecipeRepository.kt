package com.example.android_native_starter.features.recipe.data.repository

import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.recipe.data.model.Recipe
import com.example.android_native_starter.features.recipe.data.model.Recipes


import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RecipeRepository @Inject constructor(private var recipeService: IRecipeService) {

    suspend fun getAllRecipes(limit: Int, skip: Int, sortBy: String): Resource<Recipes> {
        return try {
            val result = recipeService.getAllRecipes(limit, skip, sortBy)
            Resource.Success(result)
        } catch (e: HttpException) {
            Resource.Error("Server error: ${e.code()} - ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("No internet connection. Please check your network.")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.localizedMessage ?: "Unknown error"}")
        }
    }

    suspend fun getRecipeById(id: Int): Resource<Recipe> {
        return try {
            val result = recipeService.getRecipeById(id)
            Resource.Success(result)
        } catch (e: HttpException) {
            Resource.Error("Server error: ${e.code()} - ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("No internet connection. Please check your network.")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.localizedMessage ?: "Unknown error"}")
        }
    }

    suspend fun searchRecipes(query: String): Resource<Recipes> {
        return try {
            val result = recipeService.searchRecipes(query)
            Resource.Success(result)
        } catch (e: HttpException) {
            Resource.Error("Server error: ${e.code()} - ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("No internet connection. Please check your network.")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.localizedMessage ?: "Unknown error"}")
        }
    }
}