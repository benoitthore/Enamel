package com.benoitthore.enamel.android.demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
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
import com.benoitthore.enamel.geometry.primitives.EPoint

class CanvasTestView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {

//        setLayerType(LAYER_TYPE_HARDWARE, null)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    private var cvs: Canvas? = null
    var onDrawBlock: (CanvasTestView.(Canvas) -> Unit)? = null

    override fun onDraw(canvas: Canvas) {
        cvs = canvas
        onDrawBlock?.invoke(this, canvas)
        cvs = null

    }

    fun <T : ERect> T.draw(paint: Paint) = apply {
        cvs?.drawRect(this, paint)
    }

    fun <T : ECircle> T.draw(paint: Paint) = apply {
        cvs?.drawCircle(this, paint)
    }

    fun <T : ELine> T.draw(paint: Paint) = apply {
        cvs?.drawLine(this, paint)
    }

    fun <T : ERect> T.drawRoundRect(rx: Number, ry: Number, paint: Paint) = apply {
        cvs?.drawRoundRect(this, rx, ry, paint)
    }


    private val path: Path = Path()
    fun <T : EPoint> List<T>.draw(paint: Paint, closed: Boolean = true) = apply {
        synchronized(path) {
            path.reset()
            forEachIndexed { i, p ->
                if (i == 0) {
                    path.moveTo(p.x, p.y)
                } else {
                    path.lineTo(p.x, p.y)
                }
            }
            if (closed) {
                path.close()
            }
            cvs?.drawPath(path, paint)
        }
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


fun Context.canvasView(
    init: CanvasTestView.() -> Unit = {},
    onDrawBlock: CanvasTestView.(Canvas) -> Unit
) =
    CanvasTestView(this)
        .apply { init(); this.onDrawBlock = onDrawBlock }
