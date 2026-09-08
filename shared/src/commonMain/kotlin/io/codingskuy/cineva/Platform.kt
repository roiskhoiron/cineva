package io.codingskuy.cineva

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform