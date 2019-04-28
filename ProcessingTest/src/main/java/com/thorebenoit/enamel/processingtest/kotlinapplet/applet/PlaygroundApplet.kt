package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.geometry.figures.size


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
