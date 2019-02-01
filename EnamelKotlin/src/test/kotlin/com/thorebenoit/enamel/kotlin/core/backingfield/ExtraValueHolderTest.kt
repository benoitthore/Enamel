package com.thorebenoit.enamel.kotlin.core.backingfield

import com.thorebenoit.enamel.kotlin.core.print
import org.junit.Assert.*
import org.junit.Test

class ExtraValueHolderTest {

    private val defaultValue = "defaultValue"

    private val holder =
        ExtraValueHolder<Any, String> { "defaultValue" }
    private var Any.extraValue: String by holder

    @Test
    fun test_value_assign() {
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

    }


    @Test
    fun test_re_assign_keeps_map_size_consistent() {
        val object1 = "object1"

        val value1 = "11111"
        val value2 = "22222"

        object1.extraValue = value1
        object1.extraValue = value2

        assertEquals(value2, object1.extraValue)

        assertEquals(1, holder.referenceMap.size)


    }


    val onGcCallback = { before: Int, after: Int ->
        println("GC : $before -> $after")
    }
    val byteArrayValueHolder = ExtraValueHolder<Any, ByteArray>(onGcCallback) { byteArrayOf(0) }
    var Any.someByteArray: ByteArray by byteArrayValueHolder

    @Test
    fun garabage_collection_test() {

        val bigNumber = 1_000_000
        // Allocates a lot and see if it crashes


        (0..1000).forEach {
            //            byteArrayValueHolder.referenceMap.size.print
            Math.random().toString().someByteArray = ByteArray(bigNumber)
        }

        "Finished".print

        byteArrayValueHolder.referenceMap.size.print

    }


}