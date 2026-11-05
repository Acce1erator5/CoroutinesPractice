package org.example

import kotlinx.coroutines.*

suspend fun getCurrentThreadName(): String {
    return Thread.currentThread().name
}

fun main() = runBlocking {
    println("Thread before call: ${Thread.currentThread().name}")
    val threadInsideSuspend = getCurrentThreadName()
    println("Thread returned from suspend: $threadInsideSuspend")
}
