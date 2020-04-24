package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.layout.android.extract.draw

class RectVisualEntity : DrawableVisualEntity() {

    val rect: ERectMutable =
        ERectMutable()

    var rx: Float = 0f
    var ry: Float = 0f

    fun setCornerRadius(n: Number) {
        val n = n.toFloat()
        rx = n
        ry = n
    }

    override fun onFrameUpdated() {
        rect.origin.set(frame.origin)
    }

    override fun size(toFit: ESize): ESize = rect.size

    override fun draw(canvas: Canvas) {
        canvas.withTransformation(transformation) {
            if (style.border != null) {
                canvas.draw(rect, rx, ry, drawer.borderPaint)
            }
            if (style.fill != null) {
                canvas.draw(rect, rx, ry, drawer.fillPaint)
            }
        }
    }
}