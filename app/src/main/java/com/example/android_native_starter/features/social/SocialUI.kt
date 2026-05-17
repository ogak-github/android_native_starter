package com.example.android_native_starter.features.social

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.android_native_starter.core.ui.components.EmptyView
import com.example.android_native_starter.core.ui.components.ErrorView
import com.example.android_native_starter.core.ui.components.LoadingView
import com.example.android_native_starter.core.utils.Resource
import com.example.android_native_starter.features.social.data.model.Post
import com.example.android_native_starter.features.social.viewmodel.PostUiState
import com.example.android_native_starter.features.social.viewmodel.PostViewModel

@Composable
fun SocialView(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SocialScreen(
        uiState = uiState,
        onBackClick = viewModel::onBackClicked,
        onPostClick = viewModel::onPostClicked,
        onRetry = { viewModel.loadPosts() },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialScreen(
    uiState: PostUiState,
    onBackClick: () -> Unit,
    onPostClick: (Long) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Social Feed") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onRetry) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            when (val state = uiState.postResource) {
                is Resource.Loading -> LoadingView()
                is Resource.Success -> {
                    val posts = state.data?.posts ?: emptyList()
                    if (posts.isEmpty()) {
                        EmptyView()
                    } else {
                        PostList(
                            posts = posts,
                            onPostClick = onPostClick
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
}

@Composable
fun PostList(
    posts: List<Post>,
    onPostClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(posts, key = { it.id }) { post ->
            PostItem(
                post = post,
                onClick = { onPostClick(post.id) }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostItem(
    post: Post,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "User ID: ${post.userID}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ReactionInfo(
                    icon = Icons.Default.ThumbUp,
                    count = post.reactions.likes.toString(),
                    color = MaterialTheme.colorScheme.primary
                )
                ReactionInfo(
                    icon = Icons.Default.ThumbDown,
                    count = post.reactions.dislikes.toString(),
                    color = MaterialTheme.colorScheme.error
                )
                ReactionInfo(
                    icon = Icons.Default.RemoveRedEye,
                    count = post.views.toString(),
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            FlowRow(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                post.tags.forEach { tag ->
                    AssistChip(
                        onClick = { /* Handle tag click */ },
                        label = { Text(tag) }
                    )
                }
            }
        }
    }
}

@Composable
fun ReactionInfo(
    icon: ImageVector,
    count: String,
    color: androidx.compose.ui.graphics.Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = color
        )
        Text(
            text = count,
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
    }
}
