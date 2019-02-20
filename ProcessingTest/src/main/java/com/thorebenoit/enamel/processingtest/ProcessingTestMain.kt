package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import koma.extensions.map
import koma.eye
import koma.zeros


object ProcessingTestMain {
    init {
        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false
    }


    @JvmStatic
    fun main(args: Array<String>) {
//        val m1 = zeros(3, 2).map { 2.0 }
//
//        val m2 = eye(2, 3)
//
//        m1.print
//        m2.print
//        (m2 * m1).print
    }


}






