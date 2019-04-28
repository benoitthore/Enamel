package com.thorebenoit.enamel.core.threading

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.Executors

fun <T> Deferred<T>.blockingGet(): T = runBlocking { await() }


fun singleThreadCoroutine(block: suspend CoroutineScope.() -> Unit) =
    GlobalScope.launch(Executors.newSingleThreadExecutor().asCoroutineDispatcher(), block = block)

fun coroutine(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(block = block)

inline fun coroutineDelayed(delay: Long = 100, crossinline block: suspend CoroutineScope.() -> Unit) =
    GlobalScope.launch(block = {
        delay(delay)
        block()
    })


suspend fun <T> Channel<T>.forEach(block: (T) -> Unit): List<T> {
    val list = mutableListOf<T>()

    try {
        while (true) {
            block(receive())
        }
    } catch (e: Throwable) {
    }

    return list
}

suspend fun <T> Channel<T>.receiveAll(): List<T> {
    val list = mutableListOf<T>()

    try {
        while (true) {
            list.add(receive())
        }
    } catch (e: Throwable) {
    }

    return list
}

suspend fun <T> Channel<T>.take(count: Int): List<T> {
    val list = mutableListOf<T>()

    try {
        for (i in 0 until count) {
            list.add(receive())
        }
    } catch (e: Throwable) {
    }

    return list
}



