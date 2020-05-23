package com.benoitthore.visualentity

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EStyleable

class VisualEntityDrawer(val draw: (Canvas, Paint) -> Unit) : EStyleable {

    /**
     * Update only the necessary fields, since setMesh can allocate a gradient
     */
    override var style: EStyle = EStyle()
        set(value) {
            updateStyle(oldStyle = field, newStyle = value)
            field = value
        }
    val fillPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
            .apply {
                style = Paint.Style.FILL
            }
    }
    val borderPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
            .apply {
                style = Paint.Style.STROKE
            }
    }
    val shadowPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
            .apply {
                style = Paint.Style.FILL
            }
    }

    private fun updateStyle(oldStyle: EStyle, newStyle: EStyle) {
        if (oldStyle.fill == null || oldStyle.fill != newStyle.fill) {
            fillPaint.setMesh(newStyle.fill)
        }

        borderPaint.strokeWidth = newStyle.border?.width ?: 0f
        if (oldStyle.border == null || oldStyle.border != newStyle.border) {
            borderPaint.setMesh(newStyle.border?.mesh)
        }

        // TODO Shadow
    }
}