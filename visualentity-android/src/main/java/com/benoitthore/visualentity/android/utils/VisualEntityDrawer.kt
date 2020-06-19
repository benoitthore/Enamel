package com.benoitthore.visualentity.android.utils

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EStyleable

class VisualEntityDrawer(style: EStyle, val draw: (Canvas, Paint) -> Unit) : EStyleable {

    /**
     * Update only the necessary fields, since setMesh can allocate a gradient
     */
    override var style: EStyle = style
        set(value) {
            updateStyle(oldStyle = field, newStyle = value)
            field = value
        }
    val fillPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
            .apply {
                this@apply.style = Paint.Style.FILL
            }
    }
    val borderPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
            .apply {
                this@apply.style = Paint.Style.STROKE
            }
    }
    val shadowPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
            .apply {
                this@apply.style = Paint.Style.FILL
            }
    }

    init {
        updateStyle(null, style)
    }

    private fun updateStyle(oldStyle: EStyle?, newStyle: EStyle) {
        if (oldStyle?.fill == null || oldStyle.fill != newStyle.fill) {
            fillPaint.setMesh(newStyle.fill)
        }

        if (oldStyle?.border == null || oldStyle.border != newStyle.border) {
            borderPaint.setMesh(newStyle.border?.mesh)
        }
        borderPaint.strokeWidth = newStyle.border?.width ?: 0f

        // TODO Shadow
    }
}