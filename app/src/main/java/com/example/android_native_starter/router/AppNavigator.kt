package com.example.android_native_starter.router

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import com.example.android_native_starter.core.ui.components.ActionDialogKey
import com.example.android_native_starter.core.userdata.AuthSession
import com.example.android_native_starter.features.MainKey
import com.example.android_native_starter.features.SplashScreen
import com.example.android_native_starter.features.SplashScreenKey
import com.example.android_native_starter.features.auth.LoginKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


sealed interface NavigatorInterface {
    val backStack: List<NavKey> // State yang akan diobservasi UI
    val dialogScene: DialogSceneStrategy<NavKey>
    fun navigateTo(key: NavKey)
    fun pop()
    fun resetTo(key: NavKey)
}

@Singleton
class AppNavigator @Inject constructor(
    val authSession: AuthSession,
    val externalScope: CoroutineScope
) : NavigatorInterface {

    private val _dialogScene = DialogSceneStrategy<NavKey>()

    override val dialogScene: DialogSceneStrategy<NavKey>
        get() = _dialogScene

    private val _backStack = mutableStateListOf<NavKey>(SplashScreenKey)

    override val backStack: List<NavKey>
        get() = _backStack

    private fun observeLoginState() {
        externalScope.launch {
            authSession.loginState.collectLatest { loggedIn ->
                // Ensure navigation happens on Main thread immediately
                // to prevent race conditions with rapid login/logout
                withContext(Dispatchers.Main.immediate) {
                    if (loggedIn) {
                        // Login redirect to Main
                        resetTo(MainKey)
                    } else {
                        // Logout
                        resetTo(LoginKey)
                    }
                }
            }
        }
    }

    init {
        observeLoginState()
    }



    override fun navigateTo(key: NavKey) {
        _backStack.add(key)
    }

    override fun pop() {
        _backStack.removeLastOrNull()
    }

    override fun resetTo(key: NavKey) {
        _backStack.clear()
        _backStack.add(key)
    }
}
