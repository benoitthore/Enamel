package com.benoitthore.enamel.android.bubble

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.view.doOnNextLayout
import com.benoitthore.enamel.android.demo.CanvasTestView
import com.benoitthore.enamel.android.extract.drawCircle
import com.benoitthore.enamel.android.extract.singleTouch
import com.benoitthore.enamel.geometry.figures.ECircleMutable
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.toCircle
import com.benoitthore.enamel.layout.android.dp

class BubbleView : CanvasTestView, BubbleController.View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override var bubble = ECircleMutable()

    override var handleTouch: (EPoint) -> Boolean = { false }

    private var circleSize: Number = 32.dp
        set(value) {
            field = value
            bubble.radius = value.toFloat()
        }

    private val circlePaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
    }

    override var touchListener:
                (isDown: Boolean, current: EPoint?, previous: EPoint?) -> Unit
        set(value) {
            field = value
            singleTouch(value)
        }

    init {
        touchListener = { _, _, _ -> }
        doOnNextLayout {
            bubble.set(frame.center().toCircle(circleSize))
        }
    }

    override fun update() {
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(bubble, circlePaint)
    }
}

