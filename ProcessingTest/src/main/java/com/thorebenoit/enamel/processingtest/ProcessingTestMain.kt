package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.NeuralNetwork
import com.thorebenoit.enamel.kotlin.core.math.*
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import koma.*
import koma.extensions.map
import koma.matrix.Matrix
import processing.core.PImage
import java.awt.image.BufferedImage
import com.squareup.gifencoder.GifEncoder
import com.squareup.gifencoder.ImageOptions
import com.thorebenoit.enamel.kotlin.geometry.innerCircle
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.rotation
import com.thorebenoit.enamel.kotlin.geometry.primitives.times
import java.io.FileOutputStream


object ProcessingTestMain {
    init {
        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false
    }


    @JvmStatic
    fun main(args: Array<String>) {

//        https://github.com/square/gifencoder/
        PlaygroundApplet.start(500, 500) {
            //
//            val outputStream = FileOutputStream("test.gif")
//            val options = ImageOptions()
//            val gifEncoder = GifEncoder(outputStream, width, height, 0)
//
//            fun save() {
//                _graphics.loadPixels()
//                gifEncoder.addImage(_graphics.pixels, _graphics.width, options)
//            }
//
//            fun finish() {
//                gifEncoder.finishEncoding()
//                outputStream.close()
//            }

            var zOff = 0.0f
            val nbPoint = 100

            val noiseMax = nbPoint / 5
            var i = 0f

            onDraw {
                if(i > 2){
                    return@onDraw
                }

                if (i < 1f) {
                    background(0)
                } else {
                    background(0, .5f)
                }

                val circle = eframe.innerCircle()
                val minRadius = circle.radius * 0.5
                val maxRadius = circle.radius


                stroke(255)
                noFill()
                (0 until nbPoint).map {
                    val angle = (it / nbPoint.f).rotation()

                    val xOff = Scale.map(angle.cos, -1, 1, 0, noiseMax)
                    val yOff = Scale.map(angle.sin, -1, 1, 0, noiseMax)

                    val r = Scale.map(noise(xOff, yOff, zOff), 0, 1, minRadius, maxRadius)

                    center.offsetAngle(angle, r)
                }.draw(true)


                zOff += 0.01f
                i += 0.1f
                i.print
            }


        }
    }


}

