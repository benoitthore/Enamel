package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.core.of
import com.thorebenoit.enamel.core.print
import com.thorebenoit.enamel.geometry.alignement.EAlignment
import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.geometry.figures.size
import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.geometry.layout.playground.PlaygroundServer
import com.thorebenoit.enamel.geometry.layout.playground.sendToPlayground
import com.thorebenoit.enamel.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.core.color.randomColor
import com.thorebenoit.enamel.kotlin.core.color.*


class PlaygroundApplet : KotlinPAppletLambda() {

    companion object {

        fun start(
            width: Number,
            height: Number,
            init: PlaygroundApplet .() -> Unit
        ) = start(width size height, init)

        fun start(
            size: ESize = KotlinPApplet.defaultSize,
            init: PlaygroundApplet .() -> Unit
        ) {
            KotlinPApplet.createApplet<PlaygroundApplet>(size).apply {
                frame.isResizable = true
                init()
            }


        }
    }

}

fun main() {

    val deserializer = ELayoutDeserializer()

    PlaygroundApplet.start(800, 800) {
        var layout: ELayout? = null

        layout = 3.of { ELayoutLeaf(color = red) }
            .mapIndexed { i, layout ->
                layout.sizedSquare((i + 1) * 100)
            }
            .stacked(EAlignment.rightCenter, spacing = 10)
            .arranged(EAlignment.topLeft)
            .padded(20)

        PlaygroundServer.start(deserializer = deserializer) {
            layout = it
            it.print
        }
        onDraw {
            background(255)
            layout?.arrange(eframe)
            layout?.draw()
        }
    }


    val colors = (0..10).map { randomColor() }
    50.of {
        ELayoutLeaf(
            color = red
//            colors.random()
        )
    }
        .mapIndexed { i, layout ->
            //        layout.sizedSquare((i + 1) * 100)
            layout.sizedSquare(50)
        }
        .justified(EAlignment.rightCenter)
        .snugged()
        .arranged(EAlignment.topLeft)
        .padded(20)


        .sendToPlayground()
}