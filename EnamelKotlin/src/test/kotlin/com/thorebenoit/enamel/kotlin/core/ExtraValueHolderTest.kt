package com.thorebenoit.enamel.kotlin.core

import org.junit.Assert.*
import org.junit.Test

class ExtraValueHolderTest {

    private val defaultValue = "defaultValue"
    private var Any.extraValue: String by ExtraValueHolder { "defaultValue" }

    @Test
    fun test1() {
        val object1 = "object1"
        val object2 = "object2"
        val object3 = "object3"

        val object1Value = "object1Value"
        val object2Value = "object2Value"

        object1.extraValue = object1Value
        object2.extraValue = object2Value


        assertEquals(object1Value, object1.extraValue)
        assertEquals(object2Value, object2.extraValue)
        assertEquals(defaultValue, object3.extraValue)

        // Just to be extra safe
        assertNotEquals(object1Value, object2.extraValue)
        assertNotEquals(object1Value, defaultValue)
        assertNotEquals(object2Value, object1.extraValue)
        assertNotEquals(object2Value, defaultValue)
    }
}