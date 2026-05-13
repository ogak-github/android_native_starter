package com.example.android_native_starter.features.todos.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.android_native_starter.core.ui.components.EmptyView
import com.example.android_native_starter.core.ui.components.ErrorView
import com.example.android_native_starter.core.ui.components.LoadingView
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.todos.data.model.Todo
import com.example.android_native_starter.features.todos.viewmodel.TodoUiState
import com.example.android_native_starter.features.todos.viewmodel.TodoViewModel

@Composable
fun TodoView(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TodoScreen(
        uiState = uiState,
        onBackClick = viewModel::onBackClicked,
        onRetry = viewModel::loadTodos,
        onAddTodo = viewModel::addTodo,
        onToggleTodo = viewModel::toggleTodo,
        onDeleteTodo = viewModel::deleteTodo,
        onResetAction = viewModel::resetActionState,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    uiState: TodoUiState,
    onBackClick: () -> Unit,
    onRetry: () -> Unit,
    onAddTodo: (String) -> Unit,
    onToggleTodo: (Todo) -> Unit,
    onDeleteTodo: (Int) -> Unit,
    onResetAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.actionResource) {
        when (val resource = uiState.actionResource) {
            is Resource.Error -> {
                snackbarHostState.showSnackbar(resource.message ?: "Action failed")
                onResetAction()
            }
            is Resource.Success -> {
                onResetAction()
            }
            else -> {}
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("My Todos") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (val state = uiState.todoResource) {
                is Resource.Loading -> LoadingView()
                is Resource.Success -> {
                    val todos = state.data?.todos ?: emptyList()
                    if (todos.isEmpty()) {
                        EmptyView()
                    } else {
                        TodoList(
                            todos = todos,
                            onToggleTodo = onToggleTodo,
                            onDeleteTodo = onDeleteTodo
                        )
                    }
                }
                is Resource.Error -> {
                    ErrorView(
                        message = state.message ?: "Unknown error occurred",
                        onRetry = onRetry
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        AddTodoDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { text ->
                onAddTodo(text)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Todo") },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("What needs to be done?") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = { if (text.isNotBlank()) onConfirm(text) },
                enabled = text.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun TodoList(
    todos: List<Todo>,
    onToggleTodo: (Todo) -> Unit,
    onDeleteTodo: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(todos, key = { it.id }) { todo ->
            TodoItem(
                todo = todo,
                onToggle = { onToggleTodo(todo) },
                onDelete = { onDeleteTodo(todo.id) }
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.complete,
                onCheckedChange = { onToggle() }
            )
            Text(
                text = todo.todo,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f),
                textDecoration = if (todo.complete) TextDecoration.LineThrough else TextDecoration.None
            )
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
