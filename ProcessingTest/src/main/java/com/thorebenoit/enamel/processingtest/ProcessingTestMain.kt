package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.geometry.alignement.EAlignment
import com.thorebenoit.enamel.geometry.layout.dsl.arranged
import com.thorebenoit.enamel.geometry.layout.dsl.layoutTag
import com.thorebenoit.enamel.geometry.layout.playground.sendToPlayground
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import com.thorebenoit.enamel.kotlin.core.color.*


////
import com.thorebenoit.enamel.geometry.layout.dsl.*
import com.thorebenoit.enamel.geometry.alignement.*
import com.thorebenoit.enamel.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.geometry.alignement.ERectEdge.*
import com.thorebenoit.enamel.geometry.layout.playground.sendToPlayground
//
object ProcessingTestMain {


// NOT WORKING LIKE IN EXAMPLE
//    val big = ELayoutLeaf(red)
//    val small = ELayoutLeaf(blue)
//
//    val smallLayout = small
//        .sizedSquare(200)
//        .padded(50)
//        .arranged(center, snugged = true)
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



        val layout1 = "layout1".layoutTag
        val layout2 = "layout2".layoutTag

        layout1.arranged(EAlignment.topLeft)
            .sendToPlayground()

        return

        PlaygroundApplet.start(800, 800) {

            //            val big = ELayoutLeaf(red)
//            val small = ELayoutLeaf(blue)
//
//            val smallLayout = small
//                .sizedSquare(200)
//                .padded(50)
//                .arranged(center, snugged = true)
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
//                val frame = eframe.rectAlignedInside(center, size = size)
//
//                l.arrangeAndDraw(frame)
//
////                layout.arrangeAndDraw()
                noLoop()
            }
        }

    }

}

