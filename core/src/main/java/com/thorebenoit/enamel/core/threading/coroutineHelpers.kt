package com.thorebenoit.enamel.core.threading

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.Executors

fun <T> Deferred<T>.blockingGet(): T = runBlocking { await() }




fun coroutine(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(block = block)

inline fun coroutineDelayed(delay: Long = 100, crossinline block: suspend CoroutineScope.() -> Unit) =
    GlobalScope.launch(block = {
        delay(delay)
        block()
    })



fun <T> List<T>.asChannel(): Channel<T> {
    val retChannel = Channel<T>()

    coroutine {
        forEach { retChannel.send(it) }
        retChannel.close()
    }
    return retChannel
}

suspend fun <T> Channel<T>.toList(): List<T> {
    val list = mutableListOf<T>()

    try {
        val iterator = iterator()
        while (iterator.hasNext()) {
            list += iterator.next()
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
