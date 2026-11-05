package org.example

import kotlinx.coroutines.*

fun main() = runBlocking {
    repeat(10) { i ->
        launch {
            println("download $i started on ${Thread.currentThread().name}")
            delay(500)
            println("download $i finished")
        }
    }
}
