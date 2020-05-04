package com.thorebenoit.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformableMutable

interface VisualEntity :
    ETransformableMutable {
    val intrinsicSize: ESize?
    fun draw(canvas: Canvas)
}