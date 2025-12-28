package com.example.android_native_starter.features.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.MainKey
import com.example.android_native_starter.features.auth.data.model.LoginData
import com.example.android_native_starter.core.userdata.User
import com.example.android_native_starter.core.userdata.AuthRepository
import com.example.android_native_starter.router.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val appNavigator: AppNavigator

): ViewModel() {

    private val _loginState = MutableLiveData<Resource<User>>()
    val loginState: LiveData<Resource<User>> = _loginState

    fun login(data: LoginData) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginState.postValue(Resource.Loading())
            val result = repo.login(data)
            _loginState.postValue(result)
            if (result is Resource.Success) {
                appNavigator.clearAndPush(MainKey)
            }
        }
    }
}