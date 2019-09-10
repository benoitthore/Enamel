package com.benoitthore.enamel.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.benoitthore.enamel.android.extract.drawCircle
import com.benoitthore.enamel.android.extract.drawLine
import com.benoitthore.enamel.android.extract.drawRect
import com.benoitthore.enamel.android.extract.drawRoundRect
import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable

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


    var frameMapping: (ERectMutable) -> Unit = { }
        set(value) {
            field = value
            updateFrameWithMapping()
        }

    private fun updateFrameWithMapping() {
        _frame.set(originalFrame)
        frameMapping(_frame)
    }

    val frame: ERect get() = _frame
    private var _frame: ERectMutable = ERectMutable()

    val paddedFrame: ERect get() = _paddedFrame
    private var _paddedFrame: ERectMutable = ERectMutable()

    private val originalFrame = ERectMutable()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val left = 0f
        val top = 0f
        val right = r.toFloat() - l
        val bottom = b.toFloat() - t

        originalFrame.setSides(
            left = left,
            top = top,
            right = right,
            bottom = bottom
        )

        originalFrame.padding(
            left = paddingLeft,
            right = paddingRight,
            top = paddingTop,
            bottom = paddingBottom,
            buffer = _paddedFrame
        )



        updateFrameWithMapping()

    }

}


fun Context.canvasView(onDrawBlock: CanvasTestView.(Canvas) -> Unit) =
    CanvasTestView(this).apply { this.onDrawBlock = onDrawBlock }
