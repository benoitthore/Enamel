package com.benoitthore.enamel.layout.android.visualentity.style

import android.graphics.Paint
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.layout.android.visualentity.setMesh

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


    private var lastFrame = ERectMutable()
    fun updateStyle(frame: ERect = lastFrame) {

        lastFrame.set(frame)

        with(style) {
            fill?.let { fillPaint.setMesh(frame, it) }
            border?.let {
                borderPaint.strokeWidth = it.width
                borderPaint.setMesh(frame, it.mesh)
            }
        }
    }
}