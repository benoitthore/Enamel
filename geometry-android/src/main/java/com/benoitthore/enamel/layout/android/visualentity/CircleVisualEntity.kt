package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.circle.ECircleMutable
import com.benoitthore.enamel.geometry.figures.size.ESize
import com.benoitthore.enamel.geometry.figures.size.size
import com.benoitthore.enamel.geometry.toMutable
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle

fun ECircle.toVisualEntity(style: EStyle) = CircleVisualEntity(style, this.toMutable())
class CircleVisualEntity(style: EStyle, circle: ECircleMutable = E.CircleMutable()) :
    SVGVisualEntity(),
    ECircleMutable by circle {

    constructor(style: EStyle, builder: ECircleMutable.() -> Unit) :
            this(style, E.CircleMutable().apply(builder))

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
        get() = radius * 2 size radius * 2

}