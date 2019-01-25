package com.thorebenoit.enamel.kotlin.threading

import kotlinx.coroutines.channels.BroadcastChannel

class CoroutineLock(val onUnlock: suspend () -> Unit = {}) {

    private val channel = BroadcastChannel<Int>(10)

    suspend fun wait() {
        channel.openSubscription().receive()
    }

    fun unlock() {
        coroutine {
            onUnlock()
            channel.send(0)
        }
    }
}

