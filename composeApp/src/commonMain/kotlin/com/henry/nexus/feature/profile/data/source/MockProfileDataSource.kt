package com.henry.nexus.feature.profile.data.source

import com.henry.nexus.feature.profile.domain.entity.Profile
import kotlinx.coroutines.delay

class MockProfileDataSource : ProfileDataSource {
    private var mockProfile = Profile(
        id = "1",
        username = "test_user",
        nickname = "测试用户",
        avatar = "https://picsum.photos/200",
        bio = "这是一个测试用户",
        followersCount = 100,
        followingCount = 50,
        postsCount = 30
    )

    override suspend fun getProfile(): Profile? {
        delay(1000) // 模拟网络延迟
        return mockProfile
    }

    override suspend fun updateProfile(profile: Profile) {
        delay(1000) // 模拟网络延迟
        mockProfile = profile
    }
} 