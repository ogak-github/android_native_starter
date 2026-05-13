# Project Performance Checklist

- Use `derivedStateOf` for states derived from frequently changing inputs.
- Always provide a unique `key` in Lazy Layouts.
- Avoid heavy computations inside @Composable; move to ViewModel or wrap in `remember`.
- Use lambda-based modifiers (e.g., `offset { ... }`) for high-frequency state changes.
- Ensure data models are `@Stable` or `@Immutable` to prevent unnecessary recompositions.