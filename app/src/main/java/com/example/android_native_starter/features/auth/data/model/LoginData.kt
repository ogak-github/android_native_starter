package com.example.android_native_starter.features.auth.data.model

data class LoginData(
    val username: String,
    val password: String,
    val expiresInMin: Int? = 5
)