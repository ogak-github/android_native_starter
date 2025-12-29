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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.auth.data.model.LoginData
import com.example.android_native_starter.features.auth.viewmodel.AuthViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import javax.inject.Qualifier

object LoginKey : NavKey

fun EntryProviderScope<NavKey>.loginEntryBuilder(appNavigator: AppNavigator) {
    entry(LoginKey) {
        LoginUI()
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
fun LoginUI() {
    Scaffold {
        innerPadding -> Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            LoginForm()
        }
    }
}


@Composable
fun LoginForm(

) {
    val viewModel: AuthViewModel = hiltViewModel()
    val loginState by viewModel.loginState.observeAsState()
    val visibility = remember { mutableStateOf(false) }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

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
                value = username.value,
                onValueChange = {
                   username.value = it
                },
                label = { Text("Username") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                label = { Text("Password") },
                visualTransformation = if (visibility.value) VisualTransformation.None else PasswordVisualTransformation(),
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


            when(val state = loginState) {
                is Resource.Error -> {
                    if(state.message?.contains("400") ?: false) {
                        Text("Invalid username or password", color = Color.Red)
                    } else {
                        Text("${state.message}", color = Color.Red)
                    }

                }
                is Resource.Success -> {
                    null
                }
                is Resource.Loading -> {
                    null
                }
                null -> null
            }
            ElevatedButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                   viewModel.login(LoginData(
                       username = username.value.trim(),
                       password = password.value.trim()
                   ))
                }) {
                when(val state = loginState) {
                    is Resource.Error -> {
                        Text("Login")
                    }
                    is Resource.Loading -> {
                        Text("Authenticating...")
                    }
                    is Resource.Success -> {
                        Text("Login")
                    }
                    null -> {
                        Text("Login")
                    }
                }

            }
        }
    }
}

