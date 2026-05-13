# Project Architecture

This project using MVVM, Repository pattern, Feature based architecture.
`core` folder will be the global things that used by features.
You can make a correction if the project does not follow architecture rules and pattern.

```text
app/
    core/
        data/
        utils/
        router/
    features/
        - feature1
            - data/
                model
                repository
            - viewmodel/
            - ui/
    AppEntry.kt
    MainActivity.kt
```