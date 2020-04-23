package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle

fun Paint.setMesh(frame: ERect, mesh: EStyle.Mesh) {
    alpha = ((mesh.alpha) * 255).toInt()
    when (mesh) {
        is EStyle.Mesh.Color -> {
            color = mesh.color
        }
        is EStyle.Mesh.Gradient -> {
            shader = mesh.gradient.getShader(frame)
        }
    }
}

inline fun Canvas.withTransformation(
    transformation: ETransformation,
    crossinline block: Canvas.() -> Unit
) =
    withSave {
        with(transformation) {
            translate(translation.x, translation.y)
            rotate(
                rotation.degrees,
                width * rotationPivot.x,
                height * rotationPivot.y
            )
            scale(scale.x, scale.y, scalePivot.x, scalePivot.y)
        }
        block()
    }


// TODO Replace with AndroidX
inline fun Canvas.withSave(crossinline block: Canvas.() -> Unit) = apply {
    val save = save()
    block()
    restoreToCount(save)
}