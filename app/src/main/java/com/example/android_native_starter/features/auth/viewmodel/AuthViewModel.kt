package com.example.android_native_starter.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.auth.data.model.LoginData
import com.example.android_native_starter.core.userdata.User
import com.example.android_native_starter.core.userdata.AuthRepository
import com.example.android_native_starter.core.userdata.AuthSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val loginResource: Resource<User>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class LoginUiEvent {
    data class Login(val data: LoginData) : LoginUiEvent()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val authSession: AuthSession,
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.Login -> login(event.data)
        }
    }

    private fun login(data: LoginData) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = repo.login(data)
            
            _uiState.update { 
                it.copy(
                    isLoading = false,
                    loginResource = result,
                    error = if (result is Resource.Error) result.message else null
                )
            }

            if (result is Resource.Success) {
                authSession.onLoginSuccess()
            } else if (result is Resource.Error) {
                authSession.onLogout()
            }
        }
    }
}
