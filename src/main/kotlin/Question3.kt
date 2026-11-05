package org.example

import kotlinx.coroutines.*

suspend fun loadUser() {
    delay(100)
    println("Step 1: Load User")
    checkPassword()
}

suspend fun checkPassword() {
    delay(100)
    println("Step 2: Check Password")
    openProfile()
}

suspend fun openProfile() {
    delay(100)
    println("Step 3: Open Profile")
}

suspend fun main() {
    loadUser()
}
