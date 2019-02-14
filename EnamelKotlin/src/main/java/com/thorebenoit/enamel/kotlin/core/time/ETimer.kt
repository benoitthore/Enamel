package com.thorebenoit.enamel.kotlin.core.time


open class ETimer {
    protected var startAt: Long = System.currentTimeMillis()


    val elapsed get() = System.currentTimeMillis() - startAt


    fun start() {
        startAt = System.currentTimeMillis()
    }

    override fun toString(): String {
        return "$elapsed ms"
    }


}
