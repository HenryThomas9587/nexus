package com.henry.nexus

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform