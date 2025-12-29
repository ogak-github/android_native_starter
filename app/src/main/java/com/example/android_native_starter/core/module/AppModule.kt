package com.example.android_native_starter.core.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutineScopesModule {
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
}