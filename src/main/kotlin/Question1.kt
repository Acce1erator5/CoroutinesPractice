package org.example

import kotlinx.coroutines.*

fun main() = runBlocking {
    launch {
        delay(1000)
        println("Coffee ready")
    }
    println("Order accepted")
}
