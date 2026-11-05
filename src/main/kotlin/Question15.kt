package org.example

import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Start")
    coroutineScope {
        launch { delay(300); println("X") }
        launch { delay(100); println("Y") }
        launch { delay(200); println("Z") }
    }
    println("All done")
}
