package com.benoitthore.visualentity

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.visualentity.style.Mesh
import java.lang.Exception

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
    try {
        block()
    } catch (e: Exception) {

    } finally {

        restoreToCount(save)
    }
}