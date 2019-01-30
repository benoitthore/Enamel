package com.thorebenoit.enamel.kotlin.geometry

import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker.allocationCountMap
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker.debugAllocations
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker.shouldPrint


/*
This file is used to keep track of allocations, might be removed when library is done
 */
inline fun <T> allocate(block: () -> T) = if (debugAllocations) {
    debugAllocations = false
    val ret = block()
    debugAllocations = true
    ret
} else {
    block()
}

object AllocationTracker {
    var debugAllocations = true
    var shouldPrint = true
    val allocationCountMap = mutableMapOf<Class<*>, Int>()
}


inline fun <reified T : Any> T.allocateDebugMessage() {
    if (debugAllocations) {
        val clazz = this::class.java
        if (allocationCountMap[clazz] == null) {
            allocationCountMap[clazz] = 0
        }
        val numberOfAllocations = allocationCountMap[clazz]!! + 1
        allocationCountMap[clazz] = numberOfAllocations

        if (shouldPrint) {
            println("ALLOCATING($numberOfAllocations) ${clazz.simpleName} ")
        }
    }
}