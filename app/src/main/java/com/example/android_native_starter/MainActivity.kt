package com.example.android_native_starter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation3.runtime.EntryProviderScope
import com.example.android_native_starter.core.theme.AndroidNativeStarterTheme
import com.example.android_native_starter.router.Nav3
import com.example.android_native_starter.router.NavKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var entryBuilders: Set<
            @JvmSuppressWildcards EntryProviderScope<NavKey>.() -> Unit
            >

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidNativeStarterTheme {
                Nav3(entryBuilders)
            }
        }
    }
}

