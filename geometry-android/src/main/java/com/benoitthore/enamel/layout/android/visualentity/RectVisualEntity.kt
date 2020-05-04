package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.figures.size.ESize
import com.benoitthore.enamel.geometry.toMutable
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle

fun ERect.toVisualEntity(style: EStyle) = RectVisualEntity(style, this.toMutable())
class RectVisualEntity(style: EStyle = EStyle(), rect: ERect = E.Rect()) : SVGVisualEntity(),
    ERectMutable by rect.toMutable() {

    constructor(style: EStyle, builder: ERectMutable.() -> Unit) :
            this(style, E.RectMutable().apply(builder))

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

