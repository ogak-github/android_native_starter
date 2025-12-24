package com.example.android_native_starter.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf

import androidx.compose.runtime.remember
import androidx.navigation3.runtime.EntryProviderScope

import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.android_native_starter.features.MainUI
import com.example.android_native_starter.features.auth.LoginUI

sealed class NavKey {
    object LoginUI : NavKey()
    object MainUI : NavKey()
}

@Composable
fun Nav3(
    entryBuilders: Set<EntryProviderScope<NavKey>.() -> Unit>
) {
    val backStack = remember { mutableStateListOf<Any>(NavKey.LoginUI) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entryBuilders.forEach { it }
        }
    )

   /* NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
           entry(NavKey.LoginUI) {
               LoginUI()
           }
            entry(NavKey.MainUI) {
                MainUI()
            }
        }
    )*/
}
