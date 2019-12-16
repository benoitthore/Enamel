package com.benoitthore.enamel.android.demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.benoitthore.enamel.layout.android.extract.drawCircle
import com.benoitthore.enamel.layout.android.extract.drawLine
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.line
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.layout.android.dp

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

        setPadding(16.dp, 16.dp, 16.dp, 16.dp)
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

        canvas.drawLine(frame.topLeft() line frame.bottomRight(), paint)
        canvas.drawLine(frame.topRight() line frame.bottomLeft(), paint)
        canvas.drawCircle(paddedFrame.innerCircle(), paint)
//        canvas.drawLine(0f, 0f, width.toFloat(), height.toFloat(), paint)
//        canvas.drawLine(width.toFloat(), 0f, 0f, height.toFloat(), paint)
//        canvas.drawCircle(width / 2f, height / 2f, 0.8f * min(width, height) / 2f, paint)


    }
}
