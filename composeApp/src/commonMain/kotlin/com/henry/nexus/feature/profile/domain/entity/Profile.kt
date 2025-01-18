package com.henry.nexus.feature.profile.domain.entity

data class Profile(
    val id: String,
    val username: String,
    val nickname: String?,
    val avatar: String?,
    val bio: String?,
    val followersCount: Int,
    val followingCount: Int,
    val postsCount: Int
) 