package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable

fun <T : VisualEntity> List<T>.toVisualEntityGroup() = VisualEntityGroup(this)

class VisualEntityGroup<T : VisualEntity>(list: List<T>) : VisualEntity,
    MutableList<T> by list.toMutableList() {

    override val intrinsicSize: ESize?
        get() = null

    override fun draw(canvas: Canvas) {
        canvas.withTransformation(transform) {
            forEach { it.draw(canvas) }
        }

    }

    override val transform: ETransformMutable = E.ETransformMutable()

}