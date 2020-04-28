package com.benoitthore.enamel.android.demo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import com.benoitthore.enamel.android.dp
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.primitives.div


abstract class DemoRunner {
    companion object {
        val runners = listOf<DemoRunner>(
            CircleToListOfPoint,
            RectAlignmentAnchor_Point,
            RectAlignmentAnchor_Rect,
            RectAlignmentInside,
            RectAlignmentOutside
        )
    }

    abstract val progressLabels: List<String>

    fun Canvas.frame() = E.mrect(size = E.size(width, height))
    fun Canvas.halfFrame() = E.mrectCenter(
        center = frame().center(),
        size = frame().size / 2
    )


    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            textSize = 20.dp
            color = Color.BLACK
        }

    fun Canvas.showProgress(vararg progress: Any) {

        progressLabels.forEachIndexed { i, it ->
            progress
                .getOrNull(i)
                ?.toString()
                .orEmpty()
                .let { progress ->
                    drawText(
                        it.let { "$it: $progress" },
                        16.dp,
                        (i + 1) * textPaint.textSize,
                        textPaint
                    )
                }

        }
    }

    abstract fun demo(
        canvas: Canvas,
        originalPaint: Paint,
        createdPaint: Paint,
        animatedValues: List<Float>
    )

}