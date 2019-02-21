package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.NeuralNetwork
import com.thorebenoit.enamel.kotlin.core.math.*
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import koma.*
import koma.extensions.map
import koma.matrix.Matrix
import processing.core.PImage
import java.awt.image.BufferedImage
import com.squareup.gifencoder.GifEncoder
import com.squareup.gifencoder.ImageOptions
import com.thorebenoit.enamel.kotlin.ai.genetics.random
import com.thorebenoit.enamel.kotlin.geometry.innerCircle
import com.thorebenoit.enamel.kotlin.geometry.primitives.*
import com.thorebenoit.enamel.processingtest.kotlinapplet.PAppletGifRecorder
import java.io.FileOutputStream


object ProcessingTestMain {
    init {
        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false
    }


    @JvmStatic
    fun main(args: Array<String>) {


    }


}

