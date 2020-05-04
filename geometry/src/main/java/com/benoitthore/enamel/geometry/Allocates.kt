package com.benoitthore.enamel.geometry


import com.benoitthore.enamel.geometry.AllocationTracker.allocationCountMap
import com.benoitthore.enamel.geometry.AllocationTracker.debugAllocations
import com.benoitthore.enamel.geometry.AllocationTracker.shouldPrint
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


// Used to mark functions that need to be refactored because they allocate more than needed
annotation class Allocates


/*
This file is used to keep track of allocations, might be removed when library is done
 */

inline fun <T> allocate(block: () -> T): T {
    return if (debugAllocations) {
        debugAllocations = false
        val ret = block()
        debugAllocations = true
        ret
    } else {
        block()
    }
}

@ExperimentalContracts
inline fun <T> allocateWithContract(block: () -> T): T {

    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return if (debugAllocations) {
        debugAllocations = false
        val ret = block()
        debugAllocations = true
        ret
    } else {
        block()
    }
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