package com.benoitthore.enamel.visualentity.android.utils

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EStyleable

class Drawer(style: EStyle, internal val draw: (Canvas, Paint) -> Unit) : EStyleable {

    /**
     * Update only the necessary fields, since setMesh can allocate a gradient
     */
    override var style: EStyle = style
        set(value) {
            updateStyle(oldStyle = field, newStyle = value)
            field = value
        }
    val fillPaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    val borderPaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    val shadowPaint: Paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    init {
        updateStyle(null, style)
    }

    private fun updateStyle(oldStyle: EStyle?, newStyle: EStyle) {
        if (oldStyle?.fill == null || oldStyle.fill != newStyle.fill) {
            fillPaint.setMesh(newStyle.fill)
        }

        if (oldStyle?.border == null || oldStyle.border != newStyle.border) {
            borderPaint.setMesh(newStyle.border?.mesh, stroke = true)
        }
        borderPaint.strokeWidth = newStyle.border?.width ?: 0f

        if (newStyle.shadow != null) {
            TODO("Shadows not supported yet")
        }
    }
}