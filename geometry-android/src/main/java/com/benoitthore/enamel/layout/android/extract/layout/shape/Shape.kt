package com.benoitthore.enamel.layout.android.extract.layout.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.text.TextPaint
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.primitives.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.layout.android.extract.drawRect
import com.benoitthore.enamel.layout.android.extract.layout.ECanvasLayout
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

    abstract fun onDraw(canvas: Canvas)

    abstract fun update()
}

class RectShape(rect: ERect) : Shape() {
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

inline fun Canvas.withSave(crossinline block: Canvas.() -> Unit) = apply {
    val save = save()
    block()
    restoreToCount(save)
}