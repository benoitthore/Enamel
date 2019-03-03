package com.thorebenoit.enamel.kotlin.threading

import com.thorebenoit.enamel.kotlin.core.tryCatch
import kotlinx.coroutines.*

fun <T> Deferred<T>.blockingGet(): T = runBlocking { await() }

fun coroutine(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(block = block)

fun coroutineDelayed(delay: Long = 100, block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(block = {
    delay(delay)
    block()
})


