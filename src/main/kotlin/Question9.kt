package org.example

import kotlinx.coroutines.*

fun main() = runBlocking {
    val parentJob = launch {
        launch {
            try { delay(1000) } finally { println("child 1 cleanup done") }
        }
        launch {
            try { delay(1000) } finally { println("child 2 cleanup done") }
        }
    }
    delay(100)
    parentJob.cancelAndJoin()
    println("end")
}
