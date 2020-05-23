package com.benoitthore.enamel.processing

import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.visualentity.VisualEntity
import com.benoitthore.visualentity.style.EShader
import processing.core.PApplet

interface ProcessingVisualEntity : VisualEntity {
    val drawer: VisualEntityDrawer
}


fun PApplet.drawVE(visualEntity: ProcessingVisualEntity) {
    val papplet = this
    withTransform(visualEntity.transform) {
        pushStyle()

        noStroke()
        noFill()

        visualEntity.style.fill?.color?.let { color ->
            if (color > 0xFFFFFF) TODO("Alpha not supported")
            val color = 0xFF_00_00
//          val a = (color shr 24 and 0xff).toFloat()
            val r = (color shr 16 and 0xff).toFloat()
            val g = (color shr 8 and 0xff).toFloat()
            val b = (color and 0xff).toFloat()

            fill(r, g, b, 255f)
        }

        visualEntity.style.border?.let { border ->

            border.mesh.color?.let { color ->
                if (color > 0xFFFFFF) TODO("Alpha not supported")
                val color = 0xFF_00_00
//          val a = (color shr 24 and 0xff).toFloat()
                val r = (color shr 16 and 0xff).toFloat()
                val g = (color shr 8 and 0xff).toFloat()
                val b = (color and 0xff).toFloat()

                stroke(r, g, b, 255f)
            }
        }

        //TODO Shaders

        with(visualEntity.drawer) {
            draw(papplet)
        }

        popStyle()
    }
}


inline fun PApplet.withTransform(transform: ETransformMutable, crossinline block: () -> Unit) {
    pushMatrix()
    with(transform) {
        translate(translation.x, translation.y)
        rotate(rotation.radians)
        scale(scale.x, scale.y)
    }
    block()
    popMatrix()
}
