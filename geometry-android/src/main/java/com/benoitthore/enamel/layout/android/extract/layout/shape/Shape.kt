package com.benoitthore.enamel.layout.android.extract.layout.shape

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.TextPaint
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.primitives.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.layout.android.extract.plusAssign

abstract class Shape {
    protected val paint: TextPaint = TextPaint()

    val translation: EPointMutable = EPointMutable.zero

    val rotation: EAngleMutable = EAngleMutable.zero
    val rotationPivot: EPointMutable = EPointMutable.half

    val scale: EAngleMutable = EAngleMutable.zero
    val scalePivot: EPointMutable = EPointMutable.half

    fun draw(canvas: Canvas) {
        canvas.withSave { onDraw(canvas) }
    }

    protected abstract fun onDraw(canvas: Canvas)

    protected abstract fun update()
}

open class RectShape(rect: ERect = ERect.zero) : Shape() {

    val rect: ERectMutable = rect.toMutable()
    private val path: Path = Path()

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    override fun update() {
        path.reset()
        path += rect
    }
}

class DrawableShape : RectShape() {
    private var drawable: Drawable? = null
     
    fun setDrawable(drawable: Drawable): DrawableShape {
        this.drawable = drawable
        return this
    }

    fun setBitmap(bitmap: Bitmap, resources : Resources): DrawableShape {
        drawable = BitmapDrawable(resources,bitmap)
        return this
    }

    override fun onDraw(canvas: Canvas) {
//        drawable!!.setBounds(
//            rectF.left as Int,
//            rectF.top as Int,
//            rectF.right as Int,
//            rectF.bottom as Int
//        )
//        drawable!!.draw(canvas)
    }
}


inline fun Canvas.withSave(crossinline block: Canvas.() -> Unit) = apply {
    val save = save()
    block()
    restoreToCount(save)
}