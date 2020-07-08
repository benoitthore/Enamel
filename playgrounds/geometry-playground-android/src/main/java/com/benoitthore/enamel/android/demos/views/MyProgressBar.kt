package com.benoitthore.enamel.android.demos.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.benoitthore.enamel.geometry.alignement.NamedPoint
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.line.set
import com.benoitthore.enamel.geometry.interfaces.bounds.pointAtAnchor
import com.benoitthore.enamel.layout.android.getViewBounds
import com.benoitthore.enamel.visualentity.android.draw
import com.benoitthore.enamel.visualentity.android.toAndroid
import com.benoitthore.enamel.visualentity.android.utils.dp
import com.benoitthore.visualentity.toVisualEntity

class MyProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val height = 64.dp
    val line = E.Line().toVisualEntity { strokeColor = Color.RED }.toAndroid()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(height.toInt(), MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            val bounds = getViewBounds()
            val from = bounds.pointAtAnchor(NamedPoint.centerLeft)
            val to = bounds.pointAtAnchor(NamedPoint.centerRight)
            line.set(from,to)
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.draw(line)
    }
}