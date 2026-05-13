package com.example.android_native_starter.features.todos.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_native_starter.core.userdata.UserDataService
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.todos.data.model.Todo
import com.example.android_native_starter.features.todos.data.model.Todos
import com.example.android_native_starter.features.todos.data.repository.TodoRepository
import com.example.android_native_starter.router.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TodoUiState(
    val todoResource: Resource<Todos> = Resource.Loading(),
    val actionResource: Resource<Todo>? = null
)

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repo: TodoRepository,
    private val appNavigator: AppNavigator,
    private val userDataService: UserDataService
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoUiState())
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()

    init {
        loadTodos()
    }

    fun loadTodos() {
        Log.d("TODO", "Loading Todos")
        val userId = userDataService.getUserId()
        if (userId == -1) {
            _uiState.update { it.copy(todoResource = Resource.Error("User not found. Please login again.")) }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(todoResource = Resource.Loading()) }
            val result = repo.getUserTodos(userId, 30, 0, "id")
            _uiState.update { it.copy(todoResource = result) }
        }
    }

    fun addTodo(todoText: String) {
        val userId = userDataService.getUserId()
        if (userId == -1) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(actionResource = Resource.Loading()) }
            val newTodo = Todo(
                id = 0,
                todo = todoText,
                complete = false,
                userId = userId
            )
            val result = repo.createTodo(newTodo)
            
            if (result is Resource.Success) {
                // Refresh list or update local state
                loadTodos()
            }
            _uiState.update { it.copy(actionResource = result) }
        }
    }

    fun toggleTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(actionResource = Resource.Loading()) }
            val updatedTodo = todo.copy(complete = !todo.complete)
            val result = repo.updateTodo(updatedTodo)
            
            if (result is Resource.Success) {
                loadTodos()
            }
            _uiState.update { it.copy(actionResource = result) }
        }
    }

    fun deleteTodo(todoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(actionResource = Resource.Loading()) }
            val result = repo.deleteTodo(todoId)
            
            if (result is Resource.Success) {
                loadTodos()
            }
            _uiState.update { it.copy(actionResource = result) }
        }
    }

    fun resetActionState() {
        _uiState.update { it.copy(actionResource = null) }
    }

    fun onBackClicked() {
        appNavigator.pop()
    }
}
