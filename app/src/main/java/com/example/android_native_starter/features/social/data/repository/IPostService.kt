package com.example.android_native_starter.features.social.data.repository

import com.example.android_native_starter.features.social.data.model.Post
import com.example.android_native_starter.features.social.data.model.Posts
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IPostService {
    @GET("posts")
    suspend fun getPosts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("sortBy") sortBy: String,
        @Query("order") order: String
    ) : Posts

    @GET("posts/{postId}")
    suspend fun getPostById(
        @Path("postId") postId: Int
    ): Post
}
