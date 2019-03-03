package com.thorebenoit.enamel.kotlin.threading

import com.thorebenoit.enamel.kotlin.core.randomString
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.LinkedBlockingQueue


fun Any.wait() {
    synchronized(this) {
        (this as java.lang.Object).wait()
    }
}

fun Any.notify() {
    synchronized(this) {
        (this as java.lang.Object).notify()
    }
}


class ELock {

    companion object {
        private var lastCreated = 0
    }

    private val obj =
        (lastCreated++).toString() + ('a'..'z').randomString(5) // guarantees a new lock is created every time

    fun lock() {
        obj.wait()
    }


    fun unlock() {
        obj.notify()
    }
}