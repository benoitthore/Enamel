package com.benoitthore.enamel.layout.android.extract

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.ELayoutLeaf
import com.benoitthore.enamel.geometry.layout.refs.getAllChildren
import com.benoitthore.enamel.geometry.layout.refs.getAllChildrenWithType
import com.benoitthore.enamel.layout.android.extract.layout.addToView
import com.benoitthore.enamel.layout.android.extract.layout.setupClicks

open class CanvasLayoutView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var layout: ELayout? = null
        set(value) {
            field = value
            field?.let {
                it.setupClicks(this)
                it.addToView(this)
                it.arrange(frame)
            }
            invalidate()
        }

    val paddedFrame: ERect get() = _paddedFrame
    val frame: ERect get() = _frame

    private var _frame: ERectMutable = ERectMutable()
    private var _paddedFrame: ERectMutable = ERectMutable()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val left = 0f
        val top = 0f
        val right = r.toFloat() - l
        val bottom = b.toFloat() - t

        _frame.setSides(
            left = left,
            top = top,
            right = right,
            bottom = bottom
        )

        _frame.padding(
            left = paddingLeft,
            right = paddingRight,
            top = paddingTop,
            bottom = paddingBottom,
            target = _paddedFrame
        )

        layout?.arrange(frame)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val layout = layout ?: return
        canvas.drawLayout(layout)
    }
}





























