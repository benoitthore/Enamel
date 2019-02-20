package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.figures.size


class PlaygroundApplet : KotlinPAppletLambda() {

    companion object {
        fun start(
            size: ESize = 400 size 400,
            init: PlaygroundApplet .() -> Unit
        ) {
            KotlinPApplet.createApplet<PlaygroundApplet>().apply {

//                onSettings {
//                    this.esize = size
//                }

                settings()
                setup()

                init()
            }


        }
    }

}