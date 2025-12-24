package com.example.android_native_starter.features

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.android_native_starter.features.recipe.RecipeView

@Composable
fun MainUI() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Text("Android Learn") }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            RecipeView()
        }
    }
}