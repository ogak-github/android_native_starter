package com.example.android_native_starter.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
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
import com.example.android_native_starter.features.recipe.RecipeView
import com.example.android_native_starter.router.AppNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet


@Module
@InstallIn(ActivityRetainedComponent::class)
object MainModule {
    @IntoSet
    @Provides
    fun provideMainEntryBuilder(appNavigator: AppNavigator): EntryProviderScope<NavKey>.() -> Unit = {
        mainEntryBuilder(appNavigator)
    }
}

object MainKey : NavKey

fun EntryProviderScope<NavKey>.mainEntryBuilder(appNavigator: AppNavigator) {
    entry(MainKey) {
        MainUI(
            title = "Recipe"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(title: String = "Main") {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
                },
                actions = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Logout"
                        )
                    }
                }
            )
        },

    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                RecipeView()
            }
        }
    }
}