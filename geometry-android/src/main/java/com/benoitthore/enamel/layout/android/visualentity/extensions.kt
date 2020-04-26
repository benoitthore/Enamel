package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.primitives.ETransformation
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle
import android.graphics.Rect
import android.graphics.RectF

fun Rect.set(rect: ERect) = apply {
    left = rect.left.toInt()
    top = rect.top.toInt()
    right = rect.right.toInt()
    bottom = rect.bottom.toInt()
}

fun RectF.set(rect: ERect) = apply {
    left = rect.left
    top = rect.top
    right = rect.right
    bottom = rect.bottom
}

fun Paint.setMesh(mesh: EStyle.Mesh) {
    alpha = ((mesh.alpha) * 255).toInt()
    when (mesh) {
        is EStyle.Mesh.Color -> {
            color = mesh.color
        }
        is EStyle.Mesh.Gradient -> {
            shader = mesh.gradient.shader
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