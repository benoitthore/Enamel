package com.thorebenoit.visualentity.style

import android.graphics.Paint
import com.thorebenoit.visualentity.setMesh

class VisualEntityDrawer : EStyleable {
    override var style: EStyle =
        EStyle()
        set(value) {
            field = value
            updateStyle()
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

    fun updateStyle() {
        with(style) {
            fill?.let { fillPaint.setMesh(it) }
            border?.let {
                borderPaint.strokeWidth = it.width
                borderPaint.setMesh(it.mesh)
            }
        }
    }
}