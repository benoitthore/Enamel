package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Paint
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle

abstract class ShapeVisualEntity : VisualEntity() {
    override var style: EStyle =
        EStyle()
        set(value) {
            field = value
            updateStyle()
        }
    protected val fillPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
            .apply {
                style = Paint.Style.FILL
            }
    }
    protected val borderPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
            .apply {
                style = Paint.Style.STROKE
            }
    }
    private val shadowPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
            .apply {
                style = Paint.Style.FILL
            }
    }

    abstract fun updateStyle()
}