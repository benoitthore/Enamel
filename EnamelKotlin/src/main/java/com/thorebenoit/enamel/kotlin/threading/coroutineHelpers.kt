package com.thorebenoit.enamel.kotlin.threading

import com.thorebenoit.enamel.kotlin.core.tryCatch
import kotlinx.coroutines.*
import java.util.concurrent.Executors

fun <T> Deferred<T>.blockingGet(): T = runBlocking { await() }


fun singleThreadCoroutine(block: suspend CoroutineScope.() -> Unit) =
    GlobalScope.launch(Executors.newSingleThreadExecutor().asCoroutineDispatcher(), block = block)

fun coroutine(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(block = block)

fun coroutineDelayed(delay: Long = 100, block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(block = {
    delay(delay)
    block()
})


