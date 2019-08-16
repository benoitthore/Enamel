package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.geometry.AllocationTracker.allocationCountMap
import com.benoitthore.enamel.geometry.AllocationTracker.debugAllocations
import com.benoitthore.enamel.geometry.AllocationTracker.shouldPrint


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
    var debugAllocations = false
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