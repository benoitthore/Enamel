package com.thorebenoit.enamel.kotlin.threading

import org.junit.Assert
import java.lang.Exception

class CallSequenceAssert(val debug: Boolean = false) {
    private var i = 0

    fun assertState(state: Int) {
        if (debug) {
            with(Exception().stackTrace) {
                println("$i: ${get(1)}")
            }
        }
        Assert.assertEquals(state, i++)
    }
}

class CallSequenceAssertEnum<T : Enum<*>>(val debug: Boolean = false) {
    private var i = 0

    fun  assertState(state: T) {
        if (debug) {
            with(Exception().stackTrace) {
                println("$i: ${get(1)}")
            }
        }
        Assert.assertEquals(state.ordinal, i++)
    }
}