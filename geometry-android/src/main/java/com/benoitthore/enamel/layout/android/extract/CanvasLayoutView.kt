package com.benoitthore.enamel.layout.android.extract

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable

open class CanvasLayoutView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

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
            buffer = _paddedFrame
        )

    }

}





























