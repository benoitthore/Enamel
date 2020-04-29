package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.layout.android.extract.draw
import com.benoitthore.enamel.layout.android.extract.translate
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle

class RectVisualEntity(style: EStyle = EStyle(), rect: ERect = E.Rect()) : SVGVisualEntity(),
    ERectMutable by rect.toMutable() {

    constructor(style: EStyle, builder: ERectMutable.() -> Unit) :
            this(style, E.mRect().apply(builder))

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

class CircleVisualEntity(style: EStyle, circle: ECircleMutable = E.mcircle()) : SVGVisualEntity(),
    ECircleMutable by circle {

    constructor(style: EStyle, builder: ECircleMutable.() -> Unit) :
            this(style, E.mcircle().apply(builder))

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