package com.benoitthore.enamel.android.demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.benoitthore.enamel.android.dp
import com.benoitthore.enamel.layout.android.extract.*
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.line
import com.benoitthore.enamel.geometry.innerCircle

class MyCanvasView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 4.dp.f
        color = Color.BLUE
        style = Paint.Style.STROKE
    }

    init {
        setOnClickListener { invalidate() }

        setPadding(16.dp.i, 16.dp.i, 16.dp.i, 16.dp.i)
    }

    private val frame = ERectMutable()
    private val paddedFrame = ERectMutable()

    override fun onDraw(canvas: Canvas) {

        canvas.drawColor(Color.RED)

        frame
            .set(0, 0, width, height)
        paddedFrame.set(frame)
        paddedFrame.selfPadding(
            top = paddingTop,
            left = paddingLeft,
            right = paddingRight,
            bottom = paddingBottom
        )

        canvas.draw(frame.topLeft() line frame.bottomRight(), paint)
        canvas.draw(frame.topRight() line frame.bottomLeft(), paint)
        canvas.draw(paddedFrame.innerCircle(), paint)
//        canvas.draw(0f, 0f, width.toFloat(), height.toFloat(), paint)
//        canvas.draw(width.toFloat(), 0f, 0f, height.toFloat(), paint)
//        canvas.draw(width / 2f, height / 2f, 0.8f * min(width, height) / 2f, paint)


    }
}
