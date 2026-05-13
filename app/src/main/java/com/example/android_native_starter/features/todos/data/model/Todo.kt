package com.example.android_native_starter.features.todos.data.model

data class Todos(
    val todos: List<Todo>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class Todo(
    val id: Int,
    val todo: String,
    val complete: Boolean,
    val userId: Int
)
