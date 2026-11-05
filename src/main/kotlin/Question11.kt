package org.example

import kotlinx.coroutines.*

fun CoroutineScope.startCounter(name: String, limit: Int): Job {
    return launch {
        for (i in 1..limit) {
            delay(100)
            println("Counter $name: $i")
        }
    }
}

fun main() = runBlocking {
    coroutineScope {
        startCounter("A", 3)
        startCounter("B", 2)
        println("launched")
    }
}
