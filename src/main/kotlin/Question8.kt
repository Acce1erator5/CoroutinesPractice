package org.example

import kotlinx.coroutines.*

fun main() = runBlocking {
    val parentJob = launch {
        launch {
            delay(200)
            println("child done")
        }
        println("parent body done")
    }
    parentJob.join()
    println("report completed")
}
