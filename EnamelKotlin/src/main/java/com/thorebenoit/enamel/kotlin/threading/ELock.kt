package com.thorebenoit.enamel.kotlin.threading

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
    private val obj = ""

    fun lock() {
        obj.wait()
    }


    fun unlock() {
        obj.notify()
    }
}