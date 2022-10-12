package com.example.waltz

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis


fun main() = runBlocking { // this: CoroutineScope
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }

    println("Hello")
    println("The answer is ${one.await() + two.await()}")
    println("Hi")
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

suspend fun doSomething(name: String) {
    delay(1000L)
    println("Hi ${name}")
}

