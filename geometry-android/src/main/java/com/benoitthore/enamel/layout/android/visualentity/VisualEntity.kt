package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.primitives.ETransformable
import com.benoitthore.enamel.geometry.primitives.ETransformation

interface VisualEntity : ETransformable {
    val intrinsicSize: ESize

    // Contract : updateFrame MUST update the frame variable
    fun updateFrame(frame: ERect)
    val frame : ERect

    fun draw(canvas: Canvas)
}