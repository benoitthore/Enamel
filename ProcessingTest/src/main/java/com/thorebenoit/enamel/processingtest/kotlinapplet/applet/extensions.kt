package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import processing.core.PApplet

fun KotlinPApplet._onMouseClicked(listener: () -> Unit) {
    val clickDistance = 200
    var lastMouseDown: EPoint? = null

    onMousePressed {
        if (lastMouseDown == null) {
            lastMouseDown = mousePosition.copy()
        }
    }

    onMouseReleased {
        val distance = lastMouseDown?.distanceTo(mousePosition)
        lastMouseDown = null
        if (distance != null && distance < clickDistance) {
            listener()
        }
    }

}

fun PApplet.withInvert(block: PApplet.() -> Unit) {
    pushMatrix()
    invertY()
    block()
    popMatrix()
}

fun PApplet.invertY() {
    translate(0f, height.f)
    scale(1f, -1f)
}