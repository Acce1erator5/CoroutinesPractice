package org.example

import kotlinx.coroutines.*

fun main() = runBlocking {
    coroutineScope {
        launch { delay(200); println("avatar loaded") }
        launch { delay(100); println("settings loaded") }
    }
    println("Profile ready")

    launch {
        delay(100)
        println("detached coroutine done")
    }
    println("end of main")
    delay(150)
}
