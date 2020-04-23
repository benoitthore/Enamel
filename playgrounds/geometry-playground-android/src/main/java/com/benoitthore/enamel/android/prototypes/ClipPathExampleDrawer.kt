package com.benoitthore.enamel.android.prototypes

import android.graphics.*
import com.benoitthore.enamel.android.dp
import com.benoitthore.enamel.geometry.innerRect
import com.benoitthore.enamel.geometry.toCircle
import com.benoitthore.enamel.layout.android.EFrameView
import com.benoitthore.enamel.layout.android.extract.draw
import com.benoitthore.enamel.layout.android.extract.plusAssign
import com.benoitthore.enamel.layout.android.visualentity.withSave

class ClipPathExampleDrawer(val view: EFrameView) :
    IPrototypingDrawer {

    private val paint = Paint()

    private var modeIndex = 0

    private var color
        get() = paint.color
        set(value) {
            paint.color = value
        }
    private var Paint.mode: PorterDuff.Mode
        get() = TODO()
        set(value) {
            xfermode = PorterDuffXfermode(value)
        }

    val path = Path()
    override fun draw(canvas: Canvas) {
        val rect = view.frame.center().toCircle(100.dp)
        val circle = view.frame.center().toCircle(100.dp).offset(-50.dp, 50.dp).innerRect()

        canvas.withSave {
            path += circle
            canvas.clipPath(path, Region.Op.DIFFERENCE)
            color = Color.RED
            canvas.draw(rect, paint)
        }

//        canvas.withSave {
//            path += rect
//            canvas.clipPath(path, Region.Op.INTERSECT)
//            color = RED
//            canvas.draw(circle, paint)
//        }
    }

    override fun onClick() {
        modeIndex++
    }
}