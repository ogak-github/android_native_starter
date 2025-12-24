package com.example.android_native_starter.features.recipe.data.model

data class Recipes(
    val recipes: List<Recipe>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val prepTimeMinutes: Int,
    val cookTimeMinutes: Int,
    val servings: Int,
    val difficulty: String,
    val cuisine: String,
    val caloriesPerServing: Double,
    val tags: List<String>,
    val image: String,
    val userId: Int,
    val rating: Double,
    val reviewCount: Int,
    val mealType: List<String>
)