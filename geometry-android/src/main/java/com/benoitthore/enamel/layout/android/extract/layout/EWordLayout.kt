package com.benoitthore.enamel.layout.android.extract.layout

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.geometry.layout.ELayout
import java.lang.Exception


class EWordLayout(
    val paint: Paint,
    text: CharSequence = ""
) : ECanvasLayout() {

    var text: CharSequence = text
        set(value) {
            field = value
            stringText = value.toString()
        }

    // Avoid allocation
    private var stringText = this.text.toString()

    override val children: List<ELayout> = emptyList()


    private val targetSize = ESizeMutable()

    override fun size(toFit: ESize): ESize = targetSize.set(
        paint.measureText(stringText),
        paint.textSize + paint.descent()
    )

    override fun draw(canvas: Canvas) {
        canvas.drawText(stringText, frame.x, frame.y - paint.ascent(), paint)
    }
}

class ETextLayout(
    text: CharSequence,
    val paint: TextPaint
) : ECanvasLayout() {

    private var staticLayout: StaticLayout? = null
    var text: CharSequence = text

    override fun draw(canvas: Canvas) {
        val staticLayout =
            staticLayout ?: throw Exception("Arrange function not called")

        canvas.save()
        canvas.translate(frame.left, frame.top)
        staticLayout.draw(canvas)
        canvas.restore()
    }

    override fun arrange(frame: ERect) {
        val differentFrames = frame.width != this.frame.width
        super.arrange(frame)
        if (staticLayout == null || differentFrames) {
            staticLayout = StaticLayout(
                text,
                paint,
                frame.width.toInt(),
                Layout.Alignment.ALIGN_NORMAL,
                1.0f,
                0f,
                false
            )
        }
    }

    override fun size(toFit: ESize): ESize {
        if (toFit.width < 0) {
            return ESize.zero
        }
        val staticLayout = StaticLayout(
            text,
            paint,
            toFit.width.toInt(),
            Layout.Alignment.ALIGN_NORMAL,
            1.0f,
            0f,
            false
        )
        return staticLayout.width size staticLayout.height
    }

}


class TextShape(var paint: TextPaint) {
    var text: CharSequence = ""
        private set
    private var staticLayout: StaticLayout? = null
    private var alignement: EAlignment = topLeft
        set(value) {
            field = value
            update()
        }

    private var bounds: ERectMutable = ERectMutable()

    protected fun update() {
        staticLayout = StaticLayout(
            text,
            paint,
            bounds.width.toInt(),
            Layout.Alignment.ALIGN_NORMAL,
            1.0f,
            0f,
            false
        )
    }

    val textSize: Float
        get() = paint.textSize

    private val _rect = Rect()
    fun computeTextHeight(): Float {
        paint.getTextBounds(text.toString(), 0, text.length, _rect)
        return _rect.height().toFloat()
    }

    fun draw(canvas: Canvas) {
        val staticLayout = staticLayout ?: return
        val saveState = canvas.save()
        val textHeight = calculateHeight()

        // TODO
        when (alignement) {
            topLeft -> {
            }
            topCenter -> canvas.translate(bounds.left, 0f)
            topRight -> TODO()
            bottomLeft -> canvas.translate(0f, bounds.top - textHeight)
            bottomCenter -> TODO()
            bottomRight -> TODO()
            center -> TODO()
            leftTop -> TODO()
            leftCenter -> TODO()
            leftBottom -> TODO()
            rightTop -> TODO()
            rightCenter -> TODO()
            rightBottom -> TODO()
        }
//        when {
//            verticalAlignment === Alignment.VERTICAL.CENTER -> {
//                canvas.translate(getLeft(), getCenterY() - textHeight / 2f)
//            }
//            verticalAlignment === Alignment.VERTICAL.TOP -> {
//                canvas.translate(getLeft(), getTop())
//            }
//            verticalAlignment === Alignment.VERTICAL.BOTTOM -> {
//                canvas.translate(getLeft(), getBottom() - textHeight)
//            }
//        }
        staticLayout.draw(canvas)
        canvas.restoreToCount(saveState)
    }

//    fun containsTouch(x: Float, y: Float): Boolean {
//        return false
//    }

//    fun setText(text: CharSequence?): TextShape {
//        this.text = text
//        update()
//        return this
//    }

    fun calculateHeight(): Float {
        return if (staticLayout == null || text == null || ("" + text).trim { it <= ' ' }.isEmpty()) {
            0f
        } else {
            staticLayout!!.height.toFloat()
        }
    }

}

//    private fun toAlignment(align: Alignment.HORIZONTAL): Layout.Alignment {
//        return when (align) {
//            LEFT -> Layout.Alignment.ALIGN_NORMAL
//            RIGHT -> Layout.Alignment.ALIGN_OPPOSITE
//            CENTER -> Layout.Alignment.ALIGN_CENTER
//            else -> Layout.Alignment.ALIGN_CENTER
//        }
//    }
private fun EAlignment.toLayoutAlignment(): Layout.Alignment = when (this) {
    topLeft -> TODO()
    topCenter -> TODO()
    topRight -> TODO()
    bottomLeft -> TODO()
    bottomCenter -> TODO()
    bottomRight -> TODO()
    center -> TODO()
    leftTop -> TODO()
    leftCenter -> TODO()
    leftBottom -> TODO()
    rightTop -> TODO()
    rightCenter -> TODO()
    rightBottom -> TODO()
}
