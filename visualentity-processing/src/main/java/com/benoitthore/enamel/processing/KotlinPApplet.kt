package com.benoitthore.enamel.processing

import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.rect.ERect
import processing.core.PApplet

open class KotlinPApplet : PApplet() {

    fun <T : ERect> T.draw() = apply {
        draw(this)

    }

    fun <T : ECircle> T.draw() = apply {
        draw(this)

    }

    fun <T : EOval> T.draw() = apply {
        draw(this)
    }

    fun <T : ELine> T.draw() = apply {
        draw(this)
    }

}