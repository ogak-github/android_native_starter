package com.example.android_native_starter.core.userdata

import com.example.android_native_starter.features.auth.data.model.LoginData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {
    @POST("/user/login")
    suspend fun login(
        @Body data: LoginData
    ): User
    suspend fun logout()
    suspend fun getCurrentUser(): Flow<User?>
    fun isLoggedIn(): Boolean
}