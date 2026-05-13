package com.example.android_native_starter.features.todos.data.repository

import com.example.android_native_starter.features.todos.data.model.Todo
import com.example.android_native_starter.features.todos.data.model.TodoRequest
import com.example.android_native_starter.features.todos.data.model.Todos
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query



interface ITodoService {
    @GET("todos/user/{userId}")
    suspend fun getTodosByUserId(
        @Path("userId") userId: Int,
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("sortBy") sortBy: String
    ): Todos

    @POST("todos/add")
    suspend fun createTodo(
        @Body req: TodoRequest
    ): Todo

    @DELETE("todos/{todoId}")
    suspend fun deleteTodo(
        @Path("todoId") todoId: Int
    ): Todo

    @PUT("todos/{todoId}")
    suspend fun updateTodo(
        @Path("todoId") todoId: Int,
        @Body req: TodoRequest
    ): Todo
}