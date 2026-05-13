package com.example.android_native_starter.features

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.android_native_starter.features.auth.LoginKey
import com.example.android_native_starter.router.AppNavigator
import com.example.android_native_starter.router.EntryBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object SplashModule {
    @IntoSet
    @Provides
    fun provideSplashEntryBuilder(appNavigator: AppNavigator): @JvmSuppressWildcards EntryBuilder = {
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
