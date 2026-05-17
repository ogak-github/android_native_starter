package com.example.android_native_starter.features.social.data.model

data class Posts(
    val posts: List<Post>,
    val total: Int,
    val skip: Int,
    val limit: Int
)


data class Post (
    val id: Long,
    val title: String,
    val body: String,
    val tags: List<String>,
    val reactions: Reactions,
    val views: Long,
    val userID: Long
)

data class Reactions (
    val likes: Long,
    val dislikes: Long
)

