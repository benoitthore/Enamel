package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.layout.android.extract.draw
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle

class RectVisualEntity(style: EStyle, rect: ERect = E.rect()) : BaseVisualEntity() {

    constructor(style: EStyle, builder: ERectMutable.() -> Unit) :
            this(style, E.mrect().apply(builder))

    init {
        this.style = style
    }
    val rect: ERectMutable = rect.toMutable()

    var rx: Float = 0f
    var ry: Float = 0f

    fun setCornerRadius(n: Number) {
        val n = n.toFloat()
        rx = n
        ry = n
    }

    override fun onDraw(canvas: Canvas) {
        if (style.border != null) {
            canvas.draw(rect, rx, ry, drawer.borderPaint)
        }
        if (style.fill != null) {
            canvas.draw(rect, rx, ry, drawer.fillPaint)
        }
    }

    override val intrinsicSize: ESize
        get() = rect.size
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