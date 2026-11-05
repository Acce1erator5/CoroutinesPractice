package org.example

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("--- Testing with delay ---")
    val timeDelay = measureTimeMillis {
        coroutineScope {
            launch { println("espresso start"); delay(300); println("espresso end") }
            launch { println("tea start"); delay(100); println("tea end") }
            launch { println("latte start"); delay(200); println("latte end") }
        }
    }
    println("Time with delay: $timeDelay ms\n")

    println("--- Testing with sleep ---")
    val timeSleep = measureTimeMillis {
        coroutineScope {
            launch { println("espresso start"); Thread.sleep(300); println("espresso end") }
            launch { println("tea start"); Thread.sleep(100); println("tea end") }
            launch { println("latte start"); Thread.sleep(200); println("latte end") }
        }
    }
    println("Time with sleep: $timeSleep ms")
}
