package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.EOval
import com.benoitthore.enamel.geometry.figures.EOvalMutable
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.toMutable
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle

fun EOval.toVisualEntity(style: EStyle) = OvalVisualEntity(style, this.toMutable())

class OvalVisualEntity(style: EStyle = EStyle(), oval: EOval = E.Oval()) : SVGVisualEntity(),
    EOvalMutable by oval.toMutable() {

    constructor(style: EStyle, builder: EOvalMutable.() -> Unit) :
            this(style, E.OvalMutable().apply(builder))

    init {
        this.style = style
    }

    override fun onDraw(canvas: Canvas) {
        if (style.border != null) {
            canvas.draw(this, drawer.borderPaint)
        }
        if (style.fill != null) {
            canvas.draw(this, drawer.fillPaint)
        }
    }

    override val intrinsicSize: ESize
        get() = size
}

