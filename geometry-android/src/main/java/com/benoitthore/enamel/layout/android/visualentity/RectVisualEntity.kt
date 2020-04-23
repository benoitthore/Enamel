package com.benoitthore.enamel.layout.android.visualentity

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.layout.android.extract.*

class RectVisualEntity : EStyleable, ETransformable {

    override var style: EStyle =
        EStyle()
        set(value) {
            field = value
            updateStyle()
        }

    override var transformation: ETransformation =
        ETransformation()

    val rect: ERectMutable =
        ERectMutable()

    private val fillPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
            .apply {
            style = Paint.Style.FILL
        }
    }
    private val borderPaint: Paint by lazy {
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

    fun updateStyle() {
        with(style) {
            fill?.let { fillPaint.setMesh(rect, it) }
            border?.let {
                borderPaint.strokeWidth = it.width
                borderPaint.setMesh(rect, it.mesh)
            }

            // TODO
//            shadow?.let {
//                shadowPaint.setMesh(it.mesh)
//            }
        }
    }

    fun draw(canvas: Canvas) {
        canvas.withTransformation(transformation) {
            if (style.border != null) {
                canvas.draw(rect, borderPaint)
            }
            if (style.fill != null) {
                canvas.draw(rect, fillPaint)
            }

            // TODO
//            canvas.drawRect(rect, shadowPaint)
        }
    }
}