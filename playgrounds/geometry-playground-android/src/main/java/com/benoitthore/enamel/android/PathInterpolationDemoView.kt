package com.benoitthore.enamel.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.toListOfLines
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.svg.addTo
import com.benoitthore.enamel.geometry.toImmutable
import com.benoitthore.enamel.layout.android.createSVGContext
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.enamel.layout.android.singleTouch
import com.benoitthore.enamel.layout.android.withPathMeasureData

class PathInterpolationDemoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val pathContext = Path().createSVGContext()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
        style = Paint.Style.FILL
        color = Color.GREEN
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
        strokeWidth = 2.dp
        color = Color.RED
        style = Paint.Style.STROKE
    }

    private var progress = 0f

    init {

        singleTouch {
            if (it.isDown) {
                points.clear()
            }
            points += it.position.toImmutable()

            pathContext.reset()
            (points.toListOfLines()).addTo(pathContext)
            true
        }
    }

    private val points = mutableListOf<EPoint>()

    private val rect = E.RectMutableCenter(0, 0, 100, 100)
    override fun onDraw(canvas: Canvas) {

        val data =
            pathContext.pathMeasure.getAbsolute(progress % pathContext.pathMeasure.getTotalDistance())

        canvas.drawPath(pathContext.path, strokePaint)

        canvas.withPathMeasureData(data) {
            canvas.draw(rect, paint)
        }

        progress += width  / 60f // the width per second
        postInvalidateOnAnimation()
    }
}