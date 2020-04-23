package com.benoitthore.enamel.android.prototypes

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.benoitthore.enamel.layout.android.EFrameView

class PrototypeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : EFrameView(context, attrs, defStyleAttr) {

    val drawer =
        ClipPathExampleDrawer(this)

    init {
        setOnClickListener {
            drawer.onClick()
            invalidate()
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawer.draw(canvas)
    }
}