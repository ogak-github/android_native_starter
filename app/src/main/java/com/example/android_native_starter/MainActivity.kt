package com.example.android_native_starter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.android_native_starter.core.theme.AndroidNativeStarterTheme
import com.example.android_native_starter.features.MainUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidNativeStarterTheme {
                MainUI()
            }
        }
    }
}

