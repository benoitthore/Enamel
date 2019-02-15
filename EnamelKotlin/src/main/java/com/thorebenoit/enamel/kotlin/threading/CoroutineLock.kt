package com.thorebenoit.enamel.kotlin.threading

import kotlinx.coroutines.channels.BroadcastChannel

class CoroutineLock(private val onUnlock: suspend () -> Unit = {}) {

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

class CoroutinePotentialLock() {
    private val channel = BroadcastChannel<Int>(10)

    var locked = false
        private set

    suspend fun waitAnyway() {
        locked = true
        channel.openSubscription().receive()
    }


    suspend fun waitIfLocked() {
        if (locked) {
            channel.openSubscription().receive()
        }
    }

    fun unlock() {
        coroutine {
            channel.send(0)
            locked = false
        }
    }

    fun lock() {
        locked = true

    }
}