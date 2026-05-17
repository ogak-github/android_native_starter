package com.example.android_native_starter.features.social.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.social.data.model.Post
import com.example.android_native_starter.features.social.data.model.Posts
import com.example.android_native_starter.features.social.data.repository.PostRepository
import com.example.android_native_starter.router.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class PostUiState (
    val postResource: Resource<Posts> = Resource.Loading(),
    val actionResource: Resource<Post>? = null
)

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repo: PostRepository,
    private val appNavigator: AppNavigator
): ViewModel() {

    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        Log.d("POSTS", "Load Posts")

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(postResource = Resource.Loading())
            }
            val result = repo.getPosts(20, 0, "title", "asc")
            _uiState.update {
                it.copy(postResource = result)
            }
        }
    }

    fun onBackClicked() {
        appNavigator.pop()
    }

    fun onPostClicked(postId: Long) {
        // Handle post click if needed
    }
}
