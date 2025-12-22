package com.owlyhoot.data.model

import com.google.firebase.Timestamp
import java.util.Date

data class User(
    val id: String? = null,
    val email: String? = null,
    val displayName: String? = null,
    val photoUrl: String? = null,
    val score: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
    val level: Int = 1,
    val walletAddress: String? = null,
    val bio: String? = null,
    val joinDate: Date = Date(),
    val lastActive: Date = Date(),
    val isOnline: Boolean = false
)