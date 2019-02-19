package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.figures.size


class PlaygroundApplet : KotlinPApplet() {

    var onDraw: (PlaygroundApplet.() -> Unit)? = null

    companion object {
        fun start(size: ESize = 400 size 400, onDraw: PlaygroundApplet.() -> Unit) {
            KotlinPApplet.createApplet<PlaygroundApplet>().apply {
                this.onDraw = onDraw
                this.esize = size
            }


        }
    }


    override fun draw() {
        onDraw?.invoke(this)
    }
}