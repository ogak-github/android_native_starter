package com.example.android_native_starter.router

import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import com.example.android_native_starter.core.userdata.AuthSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun providerAppNavigator(authSession: AuthSession, externalScope: CoroutineScope ): AppNavigator = AppNavigator(authSession, externalScope)

    @Provides
    fun provideBackStack(navigator: AppNavigator): List<NavKey> = navigator.backStack

    @Provides
    fun provideDialogScene(navigator: AppNavigator): DialogSceneStrategy<NavKey> = navigator.dialogScene
}
