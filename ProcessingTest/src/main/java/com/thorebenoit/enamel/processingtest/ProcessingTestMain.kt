package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import com.thorebenoit.enamel.kotlin.core.color.*


object ProcessingTestMain {


// NOT WORKING LIKE IN EXAMPLE
//    val big = ELayoutLeaf(red)
//    val small = ELayoutLeaf(blue)
//
//    val smallLayout = small
//        .sizedSquare(200)
//        .padded(50)
//        .arranged(middle, snugged = true)
//        .scaled(y = 2)
//        .arranged(topRight)
//
//    val layout = big
//        .tracked(smallLayout)
//        .height(100)
//        .padded(50)
//        .aligned(top)


    fun <T> T.asList(): List<T> = listOf(this)
    @JvmStatic
    fun main(args: Array<String>) {

        println("ok")
        return

        PlaygroundApplet.start(800, 800) {

            //            val big = ELayoutLeaf(red)
//            val small = ELayoutLeaf(blue)
//
//            val smallLayout = small
//                .sizedSquare(200)
//                .padded(50)
//                .arranged(middle, snugged = true)
//                .scaled(y = 2)
//                .arranged(topRight)
//
//            val layout = big
//                .tracked(smallLayout)
//                .height(100)
//                .padded(50)
//                .aligned(top)


            onMouseClicked { loop() }
            onDraw {

                background(255)
                fill(red)
//                listOf<Number>(1, 2, 1).rectGroupWeights(bottomCenter, eframe.size,spacing = frameCount*2).rects.forEach { it.draw() }


//                val json = ELayoutSerializer().serialize(ELayoutDemo._3()).toString()
//                val l = ELayoutDeserializer().deserialize(json)
//
//
//                val size = l.size(esize)
//                val frame = eframe.rectAlignedInside(middle, size = size)
//
//                l.arrangeAndDraw(frame)
//
////                layout.arrangeAndDraw()
                noLoop()
            }
        }

    }

}

