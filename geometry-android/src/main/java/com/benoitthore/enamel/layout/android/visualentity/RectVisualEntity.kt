package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.layout.android.extract.*
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle
import com.benoitthore.enamel.layout.android.visualentity.style.EStyleable

class RectVisualEntity : ShapeVisualEntity() {

    val rect: ERectMutable =
        ERectMutable()

    var rx: Float = 0f
    var ry: Float = 0f

    fun setCornerRadius(n: Number) {
        val n = n.toFloat()
        rx = n
        ry = n

    }

    fun draw(canvas: Canvas) {
        canvas.withTransformation(transformation) {
            if (style.border != null) {
                canvas.draw(rect, rx, ry, borderPaint)
            }
            if (style.fill != null) {
                canvas.draw(rect, rx, ry, fillPaint)
            }

            // TODO
//            canvas.drawRect(rect, shadowPaint)
        }
    }

    override fun updateStyle() {
        with(style) {
            fill?.let { fillPaint.setMesh(rect, it) }
            border?.let {
                borderPaint.strokeWidth = it.width
                borderPaint.setMesh(rect, it.mesh)
            }
        }
    }
}