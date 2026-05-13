# Project Coding Guidelines

## General Rules

- Prefer small reusable composables
- Keep composables stateless whenever possible
- Hoist state to parent composable or ViewModel
- Separate route/screen composable and content composable
- One composable should ideally have one responsibility

Avoid:
- massive composables
- nested composables too deep
- business logic inside composables
- direct repository call from composables

---

## Logic Coding Guidelines
1. ViewModel Responsibility 
   - Pure Logic: ViewModels should only handle business logic and state management, not UI styling or direct Context references. 
   - State Containers: Use StateFlow or Compose State inside ViewModels to expose data to the UI. 
   - Dependency Injection: Always use Hilt for injecting dependencies (Repositories, UseCases) into ViewModels.
2. Business Logic Location
   - Unidirectional Data Flow (UDF): UI sends events to ViewModel; ViewModel updates the State; UI observes the State. 
   - Avoid Logic in UI: Never perform data filtering, sorting, or complex calculations inside a @Composable body.
   - UseCases: For complex apps, encapsulate specific business rules into UseCase classes to keep ViewModels lean
3. Data Handling
   - Immutable State: Always use data class with val properties for UI State to ensure predictability. 
   - Repository Pattern: All data fetching (Remote or Local) must go through a Repository layer.
   - Coroutines: Use viewModelScope for launching coroutines and always specify the appropriate Dispatcher (e.g., Dispatchers.IO for disk/network).
4. Error Handling
   - Result Wrapper: Use a Result or Resource sealed class to handle Success, Error, and Loading states consistently. 
   - User Feedback: Trigger UI events (like Toasts or Snackbars) via a one-time Channel or SharedFlow to avoid re-triggering on configuration changes.

## Compose Coding Guidelines
### 1. Naming Conventions
- **PascalCase for Composables**: Composable functions must be nouns and start with an uppercase letter (e.g., `MessageList`, not `DrawMessageList`).
- **Standard Functions**: Use camelCase for helper functions that do not emit UI.

### 2. Modifier Requirements
- **Always provide a Modifier**: Every UI component should accept a `modifier: Modifier = Modifier` parameter.
- **First Optional Parameter**: Place the modifier as the first optional parameter after required arguments.
- **Root Usage**: Apply the passed-in modifier to the root layout element within the Composable.

### 3. State Management (Hoisting)
- **Stateless Composables**: Prefer creating stateless components by hoisting state to the caller.
- **Event Flow**: Pass data down (State) and pass events up (Lambdas).
- **Example**: Use `onValueChanged: (String) -> Unit` instead of passing a ViewModel into small UI pieces.

### 4. Slot APIs
- **Prefer Lambdas for Content**: Use `@Composable () -> Unit` parameters (slots) to make components flexible and reusable rather than passing many specific data types.

### 5. Performance & Recomposition
- **Remember**: Use `remember` or `rememberSaveable` to cache expensive calculations or UI state.
- **Stability**: Use stable data classes. Avoid using unstable types (like `List` from standard Kotlin) directly in state if they trigger unnecessary recompositions; consider using `PersistentList` or wrapping them.
- **Side Effects**: Only use `LaunchedEffect`, `SideEffect`, or `DisposableEffect` for non-UI operations. Never perform logic directly in the body of a Composable.

### 6. Layout Best Practices
- **Avoid Hardcoding Sizes**: Use modifiers like `fillMaxWidth()`, `weight()`, and `padding()` instead of fixed DP values where possible to ensure responsiveness.
- **Preview Support**: Always provide `@Preview` functions with dummy data for UI development and testing.

## Screen Structure

Preferred structure:

```kotlin
@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}