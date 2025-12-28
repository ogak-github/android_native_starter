package com.example.android_native_starter.core.userdata

import android.util.Log
import com.example.android_native_starter.core.userdata.IAuthService
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.auth.data.model.LoginData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private var authService: IAuthService,
    private val userDataService: UserDataService
) {

    suspend fun login(data: LoginData): Resource<User> {
        return try {
            withContext(Dispatchers.Main) {
                val result = authService.login(data)
                Log.e("AuthRepository", "Login successful: ${result}")
                
                // Save token so we stay logged in
                userDataService.saveToken(result.accessToken)
                
                Resource.Success(result)
            }

        } catch (e: HttpException) {
            Log.e("AuthRepository", "Server error: ${e.code()} - ${e.message()}")
            Resource.Error("Server error: ${e.code()} - ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("No internet connection. Please check your network.")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.localizedMessage ?: "Unknown error"}")
        }
    }

    suspend fun logout() {
        userDataService.clear()
    }

    fun isLoggedIn(): Boolean {
        // Simple check: if we have a token, we are logged in.
        return !userDataService.getToken().isNullOrEmpty()
    }
}