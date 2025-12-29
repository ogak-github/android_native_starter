package com.example.android_native_starter.features

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.android_native_starter.features.auth.LoginKey
import com.example.android_native_starter.router.AppNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object SplashModule {
    @IntoSet
    @Provides
    fun provideSplashEntryBuilder(appNavigator: AppNavigator): EntryProviderScope<NavKey>.() -> Unit = {
        splashEntryBuilder(appNavigator)
    }
}

fun EntryProviderScope<NavKey>.splashEntryBuilder(appNavigator: AppNavigator) {
    entry(SplashScreenKey) {
        SplashScreen(
            onTimeout = {
                appNavigator.resetTo(LoginKey)
            }
        )
    }
}
