package com.example.android_native_starter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.android_native_starter.core.theme.AndroidNativeStarterTheme
import com.example.android_native_starter.core.userdata.AuthSession
import com.example.android_native_starter.features.MainKey
import com.example.android_native_starter.features.auth.LoginKey
import com.example.android_native_starter.router.AppBackStack
import com.example.android_native_starter.router.Nav3
import com.example.android_native_starter.router.AppNavigator


import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: AppNavigator

    @Inject
    //@FeatureEntry
    lateinit var entryBuilder: Set<
            @JvmSuppressWildcards EntryProviderScope<NavKey>.() -> Unit
            >

    @Inject
    lateinit var authSession: AuthSession

    @Inject
    lateinit var appBackStack: AppBackStack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            val isLoggedIn by authSession.loginState.collectAsState()


            LaunchedEffect(isLoggedIn) {
                if (isLoggedIn) {
                    appBackStack.resetTo(MainKey)
                } else {
                    appBackStack.resetTo(LoginKey)
                }
            }

            AndroidNativeStarterTheme {
                Nav3(
                    entryBuilder = entryBuilder,
                    backStack = navigator.backStack)
            }
        }
    }
}

