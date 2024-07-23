package com.demax

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform