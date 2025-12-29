package com.example.android_native_starter.core.userdata

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthSession @Inject constructor(private val authRepository: AuthRepository) {

    private val _loginState = MutableStateFlow(authRepository.isLoggedIn())
    val loginState: StateFlow<Boolean> = _loginState

    fun onLoginSuccess() {
        _loginState.value = true
    }

    fun onLogout() {
        authRepository.logout()
        _loginState.value = false
    }
}