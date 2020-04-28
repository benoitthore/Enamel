package com.benoitthore.enamel.android.demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.benoitthore.enamel.android.dp
import com.benoitthore.enamel.layout.android.EFrameView

class DemoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : EFrameView(context, attrs, defStyleAttr) {

    private var animatedValues: MutableList<Float> = mutableListOf()
        set(value) {
            field = value
            invalidate()
        }

    fun setAnimatedValue(index: Int, value: Float) {
        animatedValues[index] = value
        invalidate()
    }

    var demoRunner: DemoDrawer? = null
        set(value) {
            field = value
            animatedValues = MutableList(field?.progressLabels?.size ?: 0) { 0f }
            invalidate()
        }

    private val originalPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            color = Color.BLACK
            strokeWidth = 4.dp
            style = Paint.Style.STROKE
        }
    private val createdPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            color = Color.RED
            strokeWidth = 4.dp
            style = Paint.Style.FILL_AND_STROKE
        }


    override fun onDraw(canvas: Canvas) {
        demoRunner?.demo(canvas, originalPaint, createdPaint, animatedValues)
    }
}