package com.example.android_native_starter.features

import androidx.lifecycle.ViewModel
import com.example.android_native_starter.router.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainNavigator @Inject constructor(
    val navigator: AppNavigator // Singleton di-inject ke sini
) : ViewModel()