package com.owlyhoot.data.model

import com.google.firebase.Timestamp
import java.util.Date

data class Post(
    val id: String? = null,
    val userId: String,
    val userName: String,
    val content: String,
    val imageUrl: String? = null,
    val likes: Int = 0,
    val comments: Int = 0,
    val shares: Int = 0,
    val timestamp: Date = Date(),
    val isLiked: Boolean = false
)