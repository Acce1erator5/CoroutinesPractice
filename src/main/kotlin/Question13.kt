package org.example

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job: Job = launch { }
    val deferred: Deferred<String> = async { "Data" }

    println("launch builder returns: $job")
    println("async builder returns: $deferred")

    val scopeResult = coroutineScope {
        "Scope block return value"
    }
    println("coroutineScope returns: $scopeResult")
}
