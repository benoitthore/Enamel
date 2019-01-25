package com.thorebenoit.enamel.kotlin

import com.thorebenoit.enamel.kotlin.threading.CoroutineLock
import com.thorebenoit.enamel.kotlin.threading.coroutine

object EnamelKotlinMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val lock = CoroutineLock{
            "on unlock".print
        }
        coroutine {
           lock.wait()
            "unlocked inside coroutine".print
        }

        Thread.sleep(500)

        lock.unlock()
        println("DONE")

        Thread.sleep(50)
    }

}
