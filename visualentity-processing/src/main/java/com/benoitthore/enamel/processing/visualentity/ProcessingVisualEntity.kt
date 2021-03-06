package com.benoitthore.enamel.processing.visualentity

import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.processing.VisualEntityDrawer
import com.benoitthore.visualentity.VisualEntity
import processing.core.PApplet

interface ProcessingVisualEntity : VisualEntity {
    val drawer: VisualEntityDrawer
}


fun PApplet.drawVE(vararg visualEntity: ProcessingVisualEntity) {
    visualEntity.forEach { drawVE(it) }
}

fun PApplet.drawVE(visualEntities: List<ProcessingVisualEntity>) {
    visualEntities.forEach { drawVE(it) }
}

fun PApplet.drawVE(visualEntity: ProcessingVisualEntity) {
    val papplet = this
    withTransform(visualEntity.transform) {
        pushStyle()

        noStroke()
        noFill()

        visualEntity.style.fill?.color?.let { color ->
            val a = (color shr 24 and 0xff).toFloat()
            val r = (color shr 16 and 0xff).toFloat()
            val g = (color shr 8 and 0xff).toFloat()
            val b = (color and 0xff).toFloat()

            fill(r, g, b, a)
        }

        visualEntity.style.border?.let { border ->

            border.mesh.color?.let { color ->
                val a = (color shr 24 and 0xff).toFloat()
                val r = (color shr 16 and 0xff).toFloat()
                val g = (color shr 8 and 0xff).toFloat()
                val b = (color and 0xff).toFloat()

                stroke(r, g, b, a)
            }
            visualEntity.style.border?.width?.let { strokeWeight(it) }
        }

        //TODO Shaders

        with(visualEntity.drawer) {
            draw(papplet)
        }

        popStyle()
    }
}


inline fun PApplet.withTransform(transform: ETransform, crossinline block: () -> Unit) {
    pushMatrix()
    with(transform) {
        translate(translation.x, translation.y)
        rotate(rotation.radians)
        scale(scale.x, scale.y)
    }
    block()
    popMatrix()
}
