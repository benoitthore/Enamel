package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.layout.android.extract.draw
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle

class RectVisualEntity(style: EStyle = EStyle(), rect: ERect = E.rect()) : BaseVisualEntity(),
    ERectMutable by rect.toMutable() {

    constructor(style: EStyle, builder: ERectMutable.() -> Unit) :
            this(style, E.mrect().apply(builder))

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


class CircleVisualEntity(style: EStyle, circle: ECircle = E.circle()) : BaseVisualEntity() {

    constructor(style: EStyle, builder: ECircleMutable.() -> Unit) :
            this(style, E.mcircle().apply(builder))

    init {
        this.style = style
    }

    val circle: ECircleMutable = circle.toMutable()

    override fun onDraw(canvas: Canvas) {
        if (style.border != null) {
            canvas.draw(circle, drawer.borderPaint)
        }
        if (style.fill != null) {
            canvas.draw(circle, drawer.fillPaint)
        }
    }

    override val intrinsicSize: ESize
        get() = circle.radius * 2 size circle.radius * 2

}