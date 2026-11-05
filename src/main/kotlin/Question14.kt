package org.example

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        val result = 6 * 7
    }

    val deferred = async {
        6 * 7
    }
    println("Calculated result using await(): ${deferred.await()}")
}
