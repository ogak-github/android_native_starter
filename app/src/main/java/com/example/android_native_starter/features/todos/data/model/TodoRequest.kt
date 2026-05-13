package com.example.android_native_starter.features.todos.data.model

data class TodoRequest(
    val todo: String,
    val completed: Boolean,
    val userId: Int
)