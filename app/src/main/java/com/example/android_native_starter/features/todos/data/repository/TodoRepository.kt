package com.example.android_native_starter.features.todos.data.repository

import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.todos.data.model.Todo
import com.example.android_native_starter.features.todos.data.model.TodoRequest
import com.example.android_native_starter.features.todos.data.model.Todos
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TodoRepository @Inject constructor(private var todoService: ITodoService) {

    suspend fun getUserTodos(userId: Int, limit: Int, skip: Int, sortBy: String): Resource<Todos>{
        return try {
            val result = todoService.getTodosByUserId(userId = userId, limit, skip, sortBy)
            Resource.Success(result)
        } catch (e: HttpException) {
            Resource.Error("Server error: ${e.code()} - ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("No internet connection. Please check your network.")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.localizedMessage ?: "Unknown error"}")
        }
    }

    suspend fun createTodo(todo: Todo): Resource<Todo> {
        return try {
            val result = todoService.createTodo(req =
                TodoRequest(
                    todo = todo.todo,
                    completed = todo.complete,
                    userId = todo.userId
                )
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


    suspend fun deleteTodo(todoId: Int): Resource<Todo> {
        return try {
            val result = todoService.deleteTodo(todoId)
            Resource.Success(result)
        } catch (e: HttpException) {
            Resource.Error("Server error: ${e.code()} - ${e.message()}")
        } catch (e: IOException) {
            Resource.Error("No internet connection. Please check your network.")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.localizedMessage ?: "Unknown error"}")
        }
    }

    suspend fun updateTodo(todo: Todo): Resource<Todo> {
        return try {
            val result = todoService.updateTodo(todo.id, TodoRequest(
                todo = todo.todo,
                completed = todo.complete,
                userId = todo.userId,
            ))
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