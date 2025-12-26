package com.example.android_native_starter.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.Navigator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.android_native_starter.features.MainUI
import com.example.android_native_starter.features.auth.LoginUI



@Composable
fun Nav3(
    entryBuilders: Set<EntryProviderScope<NavKey>.() -> Unit>,
    backStack: SnapshotStateList<NavKey>
) {


    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entryBuilders.forEach { it() }
        }
    )
}



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
