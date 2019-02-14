package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint

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
