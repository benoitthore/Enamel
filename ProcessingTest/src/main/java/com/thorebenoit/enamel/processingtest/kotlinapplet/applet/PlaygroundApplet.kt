package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.playground.PlaygroundServer
import processing.core.PConstants


class PlaygroundApplet : KotlinPAppletLambda() {

    companion object {

        fun start(
            width: Number,
            height: Number,
            init: PlaygroundApplet .() -> Unit
        ) = start(width size height, init)

        fun start(
            size: ESizeType = KotlinPApplet.defaultSize,
            init: PlaygroundApplet .() -> Unit
        ) {
            KotlinPApplet.createApplet<PlaygroundApplet>(size).apply {
                init()
            }


        }
    }

}

fun main() {

//    PlaygroundApplet.start(800, 800) {
//
//
//        onDraw {
//
//            background(255)
//
//            val serializer = ELayoutSerializerDigital.createIntIDSerializer()
//            val layout =
//                2.of {
//                    ELayoutLeaf(com.thorebenoit.enamel.kotlin.core.color.red)
//                        .sized(250, 250)
//                }
//                    .stackedRightCenter()
//                    .snugged()
//                    .arranged(EAlignment.topLeft)
//                    .padded(5)
//
//            serializer.add(layout)
//            layout.arrange(eframe)
////            layout.draw()
//
//            serializer.toDeserializer().readLayout().let { layout2 ->
//                layout2.arrange(eframe)
//
//                layout.print
//                layout2.print
//
//                layout2.draw()
//                ""
//            }
//
//            noLoop()
//        }
//    }

    PlaygroundApplet.start(800, 800) {
        var layout: ELayout? = null
        PlaygroundServer().start {
            layout = it
            it.print
        }
        onDraw {
            background(255)
            layout?.arrange(eframe)
            layout?.draw()
        }
    }
}