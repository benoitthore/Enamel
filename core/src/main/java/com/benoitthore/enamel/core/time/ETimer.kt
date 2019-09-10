package com.benoitthore.enamel.core.time

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
    var isStarted = false
        private set

    val elapsed get() = if (isStarted) System.currentTimeMillis() - startAt else 0


    fun start() {
        isStarted = true
        startAt = System.currentTimeMillis()
    }

    fun stop() {
        isStarted = false
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
