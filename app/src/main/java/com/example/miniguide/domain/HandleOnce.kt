package com.example.miniguide.domain

open class HandleOnce<out T>(private val content: T) {
    private var alreadyHandled = false

    fun getContentIfNotHandled(): T? {
        if (alreadyHandled) return null
        alreadyHandled = true
        return content
    }
}