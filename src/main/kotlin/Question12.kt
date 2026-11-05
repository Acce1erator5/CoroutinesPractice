package org.example

import kotlinx.coroutines.*

fun CoroutineScope.greetBackground() {
    launch {
        delay(100)
        println("Hello from background!")
    }
}

suspend fun greetSeveralWait(names: List<String>) = coroutineScope {
    names.forEach { name ->
        launch {
            delay(50)
            println("Hello, $name")
        }
    }
}

fun main() = runBlocking {
    greetBackground()
    println("Line after greetBackground call")

    greetSeveralWait(listOf("Alice", "Bob"))
    println("Line after greetSeveralWait call")
}
