package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.NeuralNetwork
import com.thorebenoit.enamel.kotlin.core.math.*
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import koma.*
import koma.extensions.map
import koma.matrix.Matrix


object ProcessingTestMain {
    init {
        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false
    }


    @JvmStatic
    fun main(args: Array<String>) {
        val nn = NeuralNetwork(
            3,
            2,
            1
        )

        nn.feedForward(listOf(1,2,3)).print

    }


}

