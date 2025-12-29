package com.example.android_native_starter.router

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.view.VelocityTrackerCompat.clear
import androidx.navigation3.runtime.NavKey
import com.example.android_native_starter.core.userdata.AuthSession
import com.example.android_native_starter.features.MainKey
import com.example.android_native_starter.features.auth.LoginKey
import com.example.android_native_starter.features.auth.LoginUI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppBackStack @Inject constructor(
    authSession: AuthSession
) {
    val backStack = mutableStateListOf<NavKey>().apply {
        add(
            if (authSession.loginState.value)
                MainKey
            else
                LoginKey
        )
    }

    fun resetTo(key: NavKey) {
        backStack.clear()
        backStack.add(key)
    }
}


@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideBackStack(
        appBackStack: AppBackStack
    ): SnapshotStateList<NavKey> = appBackStack.backStack
}
