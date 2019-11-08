package com.benoitthore.enamel.layout.android.extract.layout

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.layout.android.extract.ETouchListener
import com.benoitthore.enamel.layout.android.extract.OnClickTouchListener
import com.benoitthore.enamel.layout.android.extract.SingleTouchDelegate
import com.benoitthore.enamel.layout.android.extract.set

class EClickLayout(val child: ELayout, val callback: () -> Unit) : ECanvasLayout() {
    internal val touchListener = OnClickTouchListener(getFrame = { frame }, callback = callback)

    override fun size(toFit: ESize): ESize = child.size(toFit)

    override fun draw(canvas: Canvas) = child.draw(canvas)

    override fun arrange(frame: ERect) {
        super.arrange(frame)
        child.arrange(frame)
    }

    override val children: List<ELayout> = listOf(child)
}
