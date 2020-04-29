package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.primitives.ETransformation
import android.graphics.Rect
import android.graphics.RectF
import com.benoitthore.enamel.layout.android.visualentity.style.Mesh

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

fun Paint.setMesh(mesh: Mesh) {
    mesh.color?.let { color = it }
    mesh.shader?.let { shader = it.shader }
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


// Not using AndroidX to avoid conflicts
inline fun Canvas.withSave(crossinline block: Canvas.() -> Unit) = apply {
    val save = save()
    block()
    restoreToCount(save)
}