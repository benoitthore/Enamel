package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.primitives.ETransformable
import com.benoitthore.enamel.layout.android.extract.PathSVGContext

interface VisualEntity : ETransformable {
    val intrinsicSize: ESize?
    fun draw(canvas: Canvas)
}