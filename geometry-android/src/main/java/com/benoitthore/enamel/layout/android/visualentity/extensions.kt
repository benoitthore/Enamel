package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.ETransform
import android.graphics.Rect
import android.graphics.RectF
import com.benoitthore.enamel.geometry.primitives.EAngle
import com.benoitthore.enamel.geometry.primitives.EPoint
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


inline fun Canvas.withTranslate(
    translation: EPoint,
    crossinline block: Canvas.() -> Unit
) = withSave {
    translate(translation.x, translation.y)
    block()
}

inline fun Canvas.withRotation(
    angle: EAngle,
    pivot: EPoint? = null,
    crossinline block: Canvas.() -> Unit
) = withSave {
    if (pivot == null) {
        rotate(angle.degrees)
    } else {
        rotate(angle.degrees, pivot.x, pivot.y)
    }
    block()
}

inline fun Canvas.withScale(
    scale: EPoint,
    pivot: EPoint? = null,
    crossinline block: Canvas.() -> Unit
) = withSave {
    if (pivot == null) {
        scale(scale.x, scale.y)
    } else {
        scale(scale.x, scale.y, pivot.x, pivot.y)
    }
    block()
}

inline fun Canvas.withTransformable(
    transformable: ETransform,
    crossinline block: Canvas.() -> Unit
) = withTransformation(transformable, block)

inline fun Canvas.withTransformation(
    transform: ETransform,
    crossinline block: Canvas.() -> Unit
) {
    with(transform) {
        withTranslate(translation) {
            withRotation(rotation, rotationPivot) {
                withScale(scale, scalePivot) {
                    block()
                }
            }
        }
    }
}


inline fun Canvas.withSave(crossinline block: Canvas.() -> Unit) = apply {
    val save = save()
    runCatching { block() }
    restoreToCount(save)
}