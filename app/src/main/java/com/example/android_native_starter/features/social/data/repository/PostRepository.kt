package com.example.android_native_starter.features.social.data.repository

import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.social.data.model.Post
import com.example.android_native_starter.features.social.data.model.Posts
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: IPostService
) {
    suspend fun getPosts(
        limit: Int,
        skip: Int,
        sortBy: String,
        orderBy: String
    ): Resource<Posts> {
        return try {
            val result = postService.getPosts(
                limit, skip, sortBy, orderBy
            )
            Resource.Success(result)
        } catch (e: HttpException) {
            Resource.Error("Server error: ${e.code()} - ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("No internet connection. Please check your network.")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.localizedMessage ?: "Unknown error"}")
        }
    }

    suspend fun getPostById(postId: Int): Resource<Post> {
        return try {
            val result = postService.getPostById(postId)
            Resource.Success(result)
        } catch (e: HttpException) {
            Resource.Error("Server error: ${e.code()} - ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("No internet connection. Please check your network.")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.localizedMessage ?: "Unknown error"}")
        }
    }

}
