package com.example.android_native_starter.features.auth


import androidx.compose.foundation.layout.Box
import com.example.android_native_starter.router.AppNavigator
import com.example.android_native_starter.features.MainKey
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

object LoginUI : NavKey

fun EntryProviderScope<NavKey>.loginEntryBuilder(appNavigator: AppNavigator) {
    entry(LoginUI) {
        LoginUI(
            onLoginSuccess = {
                // Navigate to MainUI when login is successful
                appNavigator.clearAndPush(MainKey)
            }
        )
    }
}


@Module
@InstallIn(ActivityRetainedComponent::class)
object LoginFeatureModule {
    @IntoSet
    @Provides
    fun provideLoginEntryBuilder(appNavigator: AppNavigator): EntryProviderScope<NavKey>.() -> Unit = {
        loginEntryBuilder(appNavigator)
    }
}


@Composable
fun LoginUI(onLoginSuccess: () -> Unit) {
    Scaffold {
        innerPadding -> Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            LoginForm(
                onLoginSuccess = onLoginSuccess
            )
        }
    }
}

@Composable
fun LoginForm(
    onLoginSuccess: () -> Unit
) {
    val visibility = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Username") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Password") },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            visibility.value = !visibility.value
                        }
                    ) {
                        Icon(
                            imageVector = if (visibility.value)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ElevatedButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    // Direct login - no validation logic
                    onLoginSuccess()
                }) {
                Text("Login")
            }
        }
    }
}

