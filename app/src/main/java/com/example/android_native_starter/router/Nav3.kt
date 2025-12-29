package com.example.android_native_starter.router

import androidx.compose.runtime.Composable

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay

@Composable
fun Nav3(
    entryBuilder: Set<EntryProviderScope<NavKey>.() -> Unit>,
    backStack: SnapshotStateList<NavKey>,
) {
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
          entryBuilder.forEach { it() }
        },
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
