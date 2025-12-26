package com.example.android_native_starter.router

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import javax.inject.Inject


sealed interface NavigatorInterface {
     val backStack: SnapshotStateList<NavKey>
     fun push(key: NavKey)
     fun pop()
}

@javax.inject.Singleton
class AppNavigator @Inject constructor(
    override val backStack: SnapshotStateList<NavKey>
) : NavigatorInterface {
    override fun push(key: NavKey) {
        backStack.add(key)
    }

    override fun pop() {
        backStack.removeLastOrNull()
    }

    fun clearAndPush(key: NavKey) {
        backStack.clear()
        backStack.add(key)
    }
}
