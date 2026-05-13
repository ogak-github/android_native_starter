package com.example.android_native_starter.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import com.example.android_native_starter.core.ui.components.ActionDialogComponent
import com.example.android_native_starter.core.ui.components.ActionDialogKey
import com.example.android_native_starter.features.mainmenu.MainMenuScreen
import com.example.android_native_starter.features.mainmenu.MainNavigator
import com.example.android_native_starter.features.quotes.QuotesUI
import com.example.android_native_starter.features.recipe.RecipeView
import com.example.android_native_starter.features.todos.ui.TodoView
import com.example.android_native_starter.router.AppNavigator
import com.example.android_native_starter.router.EntryBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @IntoSet
    @Provides
    fun provideMainEntryBuilder(appNavigator: AppNavigator): @JvmSuppressWildcards EntryBuilder = {
        mainEntryBuilder(appNavigator)
    }
}

object MainKey : NavKey
object RecipeKey : NavKey
object QuotesKey : NavKey
object TodoKey : NavKey

fun EntryProviderScope<NavKey>.mainEntryBuilder(appNavigator: AppNavigator) {
    entry(ActionDialogKey, metadata = DialogSceneStrategy.dialog(
        DialogProperties(windowTitle = "Logout", dismissOnClickOutside = true, dismissOnBackPress = true)
    )) { _ ->
        ActionDialogComponent(
            onDismissRequest = {
                appNavigator.pop()
            },
            onConfirmation = {
                appNavigator.authSession.onLogout()
            },
            dialogTitle = "Logout",
            dialogText = "Are you sure you want to logout?",
            icon = Icons.AutoMirrored.Filled.Logout
        )
    }

    entry(MainKey) {
        MainRoute(title = "Home")
    }

    entry(QuotesKey) {
        QuotesUI()
    }

    entry(RecipeKey) {
        RecipeView()
    }

    entry(TodoKey) {
        TodoView()
    }
}

@Composable
fun MainRoute(
    title: String,
    modifier: Modifier = Modifier,
    viewModel: MainNavigator = hiltViewModel()
) {
    MainScreen(
        title = title,
        onLogoutClick = { viewModel.navigator.navigateTo(ActionDialogKey) },
        onMenuItemClick = { itemId ->
            when (itemId) {
                "recipe" -> viewModel.navigator.navigateTo(RecipeKey)
                "quotes" -> viewModel.navigator.navigateTo(QuotesKey)
                "todos" -> viewModel.navigator.navigateTo(TodoKey)
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    title: String,
    onLogoutClick: () -> Unit,
    onMenuItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
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
                MainMenuScreen(
                    onMenuItemClick = onMenuItemClick
                )
            }
        }
    }
}
