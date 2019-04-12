package com.thorebenoit.enamel.core.time

open class ETimer {

    companion object {
        fun time(block: () -> Unit) = ETimer().time(block)
        fun printTime(block: () -> Unit) {
            println(
                "${ETimer().time(block)} ms"
            )
        }
    }

    protected var startAt: Long = System.currentTimeMillis()

    val elapsed get() = System.currentTimeMillis() - startAt


    fun start() {
        startAt = System.currentTimeMillis()
    }

    override fun toString(): String {
        return "$elapsed ms"
    }

    fun time(block: () -> Unit): Long {
        start()
        block()
        return elapsed
    }

}
