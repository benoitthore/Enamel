package com.benoitthore.enamel.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.figures.ERect

class CanvasTestView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var cvs: Canvas? = null
    var onDrawBlock: (CanvasTestView.(Canvas) -> Unit)? = null

    override fun onDraw(canvas: Canvas) {
        cvs = canvas
        onDrawBlock?.invoke(this, canvas)
        cvs = null

    }

    fun ERect.draw(paint: Paint) {
        cvs?.drawRect(this, paint)
    }

    fun ECircle.draw(paint: Paint) {
        cvs?.drawCircle(this, paint)
    }

    fun ELine.draw(paint: Paint) {
        cvs?.drawLine(this, paint)
    }

    fun ERect.drawRoundRect(rx: Number, ry: Number, paint: Paint) {
        cvs?.drawRoundRect(this, rx, ry, paint)
    }
}


fun Context.canvasView(onDrawBlock: CanvasTestView.(Canvas) -> Unit) =
    CanvasTestView(this).apply { this.onDrawBlock = onDrawBlock }
