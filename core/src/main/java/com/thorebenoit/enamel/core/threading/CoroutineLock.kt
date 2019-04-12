package com.thorebenoit.enamel.core.threading

import com.thorebenoit.enamel.core.threading.coroutine
import kotlinx.coroutines.channels.BroadcastChannel

class CoroutineLock(private val onUnlock: suspend () -> Unit = {}) {

    private var isLocked = false
    private val channel = BroadcastChannel<Int>(10)

    suspend fun wait() {
        isLocked = true
        channel.openSubscription().receive()
    }

    fun unlock() {
        if (!isLocked) {
            return
        }
        coroutine {
            isLocked = false
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