package com.benoitthore.enamel.android.demos.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.*
import android.util.AttributeSet
import android.view.View
import com.benoitthore.enamel.geometry.alignement.*
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.set
import com.benoitthore.enamel.geometry.interfaces.bounds.diagonalTLBR
import com.benoitthore.enamel.geometry.interfaces.bounds.pointAtAnchor
import com.benoitthore.enamel.geometry.primitives.point.point
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.layout.android.getViewBounds
import com.benoitthore.enamel.layout.android.singleTouch
import com.benoitthore.enamel.visualentity.android.draw
import com.benoitthore.enamel.visualentity.android.drawFromCenter
import com.benoitthore.enamel.visualentity.android.toAndroid
import com.benoitthore.enamel.visualentity.android.utils.dp
import com.benoitthore.visualentity.style.style
import com.benoitthore.visualentity.style.toShader
import com.benoitthore.visualentity.toVisualEntity

class MyProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val height = 64.dp

    private val progressCircle = E.Circle(radius = 32.dp)
        .toVisualEntity { circle ->
            fillShader = circle.toShader(RED, YELLOW)
            strokeColor = BLACK
            strokeWidth = 1.dp
        }.toAndroid()

    private val line = E.Line().toVisualEntity { strokeColor = RED }.toAndroid()

    init {
        singleTouch {
//            progressCircle.centerX = it.position.x.coerceIn(line.start.x, line.end.x)
//            progressCircle.centerY = line.start.y
            line.projectedPoint(it.position,target = progressCircle.center)
            invalidate()
            true
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(height.toInt(), MeasureSpec.EXACTLY)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val bounds = getViewBounds()
        val from = bounds.pointAtAnchor(NamedPoint.centerLeft)
        val to = bounds.pointAtAnchor(NamedPoint.centerRight)
        line.set(from, to)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.draw(line)
        canvas.draw(progressCircle)
    }
}

fun main(canvas: Canvas) {

    val circleVE = E.Circle(radius = 10.dp).toVisualEntity { circle ->
        strokeColor = RED
        strokeWidth = 2.dp
        fillShader = circle
            .diagonalTLBR()
            .toShader(RED, Color.YELLOW)
    }.toAndroid()

    canvas.drawFromCenter {
        draw(circleVE)
    }


}