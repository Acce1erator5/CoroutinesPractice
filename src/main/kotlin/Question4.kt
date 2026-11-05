package org.example

import kotlinx.coroutines.*

suspend fun loadUserForQ4() {
    delay(100)
    println("User Loaded")
}

fun fixWay1() {
    runBlocking {
        loadUserForQ4()
    }
}

fun fixWay2(scope: CoroutineScope) {
    scope.launch {
        loadUserForQ4()
    }
}

fun main() = runBlocking {
    println("Executing Way 1:")
    fixWay1()

    println("Executing Way 2:")
    fixWay2(this)
}
