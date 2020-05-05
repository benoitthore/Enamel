package com.benoitthore.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.line.ELineMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.toMutable
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.style.*

fun ELine.toVisualEntity(style: EStyle) = LineVisualEntity(style, this.toMutable())

class LineVisualEntity(style: EStyle, line: ELineMutable = E.LineMutable()) :
    SVGVisualEntity(),
    ELineMutable by line {

    constructor(style: EStyle, builder: ELineMutable.() -> Unit) :
            this(style, E.LineMutable().apply(builder))

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
        get() = getBounds().size

}