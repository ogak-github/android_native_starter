package com.example.android_native_starter.router

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay

@Composable
fun Nav3(
    entryBuilder: Set<EntryProviderScope<NavKey>.() -> Unit>,
    backStack: List<NavKey>,
    sceneStrategy: DialogSceneStrategy<NavKey>
) {
   /* NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
          entryBuilder.forEach { it() }
        },
        sceneStrategy = sceneStrategy
    )*/

        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider {
                entryBuilder.forEach { it() }
            },
            sceneStrategies = listOf(sceneStrategy),
            transitionSpec = { sharedSlideIn },
            popTransitionSpec = { sharedSlidePop },
            predictivePopTransitionSpec = { sharedSlidePop }
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
