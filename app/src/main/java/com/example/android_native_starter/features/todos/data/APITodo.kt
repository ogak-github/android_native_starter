package com.example.android_native_starter.features.todos.data

import com.example.android_native_starter.features.todos.data.repository.ITodoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APITodo {

    @Singleton
    @Provides
    fun provideTodoService(retrofit: Retrofit): ITodoService {
        return retrofit.create(ITodoService::class.java)
    }
}
