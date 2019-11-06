package com.benoitthore.enamel.layout.android.extract.layout

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.figures.ESizeMutable
import com.benoitthore.enamel.geometry.layout.ELayout


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


    private val bufferSize = ESizeMutable()

    override fun size(toFit: ESize): ESize = bufferSize.set(
        paint.measureText(stringText),
        paint.textSize + paint.descent()
    )


    override fun draw(canvas: Canvas) {
        canvas.drawText(stringText, frame.x, frame.y - paint.ascent(), paint)
    }

}
