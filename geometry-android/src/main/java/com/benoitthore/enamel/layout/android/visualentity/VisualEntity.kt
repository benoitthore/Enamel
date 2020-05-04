package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.figures.size.ESize
import com.benoitthore.enamel.geometry.primitives.ETransformable
import com.benoitthore.enamel.geometry.primitives.ETransformableMutable

interface VisualEntity : ETransformableMutable {
    val intrinsicSize: ESize?
    fun draw(canvas: Canvas)
}