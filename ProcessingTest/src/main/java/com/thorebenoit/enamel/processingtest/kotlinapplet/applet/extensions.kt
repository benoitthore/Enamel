package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.core.math.f
import com.thorebenoit.enamel.geometry.primitives.EPointMutable
import processing.core.PApplet

fun KotlinPApplet._onMouseClicked(listener: () -> Unit) {
    val clickDistance = 200
    var lastMouseDown: EPointMutable? = null

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

inline fun <T : PApplet> T.pushPop(block: T.() -> Unit) {
    pushStyle()
    pushMatrix()

    block()

    popMatrix()
    popStyle()
}