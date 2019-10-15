package com.benoitthore.enamel.processingtest

import com.benoitthore.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet

object ProcessingTestMain {

    @JvmStatic
    fun main(args: Array<String>) {

        PlaygroundApplet.start(800, 800) {
            onDraw {
                background(255)
            }
        }
    }
}

