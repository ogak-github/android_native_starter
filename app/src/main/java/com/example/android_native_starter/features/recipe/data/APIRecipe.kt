package com.example.android_native_starter.features.recipe.data


import com.example.android_native_starter.features.recipe.data.repository.IRecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class APIRecipe {

    @Singleton
    @Provides
    fun providerRecipeService(retrofit: Retrofit) : IRecipeService {
        return retrofit.create(IRecipeService::class.java)
    }

}

