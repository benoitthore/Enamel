package com.benoitthore.enamel.android.demo

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.enamel.android.dp
import com.benoitthore.enamel.core._2dec
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.core.math.lerp
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.primitives.rotations
import com.benoitthore.enamel.layout.android.extract.draw
import com.benoitthore.enamel.layout.android.extract.drawPointList

object CircleToListOfPoint : DemoRunner() {

    override val progressLabels: List<String> = listOf("numberOfPoint", "startAt (angle)")

    override fun demo(
        canvas: Canvas,
        originalPaint: Paint,
        createdPaint: Paint,
        animatedValues: List<Float>
    ) {
        val progress1 = animatedValues[0].lerp(3, 20).i
        val progress2 = animatedValues[1].rotations()
        canvas.showProgress(progress1, progress2)

        val originalShape = canvas.halfFrame().innerCircle()
        val createdShape =
            originalShape.toListOfPoint(
                numberOfPoint = progress1,
                startAt = progress2
            )

        canvas.draw(originalShape, originalPaint)
        canvas.drawPointList(createdShape, 8.dp, createdPaint)
    }

}


object RectAlignmentOutside : DemoRunner() {

    override val progressLabels: List<String> = listOf("alignment", "size")

    override fun demo(
        canvas: Canvas,
        originalPaint: Paint,
        createdPaint: Paint,
        animatedValues: List<Float>
    ) {
        val progress1 = EAlignment.values().toList()[animatedValues[0]]
        val progress2 = E.msizeSquare(animatedValues[1].lerp(32.dp, 100.dp))

        canvas.showProgress(progress1, progress2.width)

        val originalShape = canvas.halfFrame()
        val createdShape = originalShape
            .rectAlignedOutside(alignment = progress1, size = progress2, spacing = 16.dp)

        canvas.draw(originalShape, originalPaint)
        canvas.draw(createdShape, createdPaint)
    }

}

object RectAlignmentInside : DemoRunner() {

    override val progressLabels: List<String> = listOf("alignment", "size")

    override fun demo(
        canvas: Canvas,
        originalPaint: Paint,
        createdPaint: Paint,
        animatedValues: List<Float>
    ) {
        val progress1 = EAlignment.values().toList()[animatedValues[0]]
        val progress2 = E.msizeSquare(animatedValues[1].lerp(32.dp, 100.dp))

        canvas.showProgress(progress1, progress2.width)

        val originalShape = canvas.halfFrame()
        val createdShape = originalShape
            .rectAlignedInside(alignment = progress1, size = progress2, spacing = 16.dp)

        canvas.draw(originalShape, originalPaint)
        canvas.draw(createdShape, createdPaint)
    }

}


object RectAlignmentAnchor_Point : DemoRunner() {

    override val progressLabels: List<String> = listOf("x", "y")

    override fun demo(
        canvas: Canvas,
        originalPaint: Paint,
        createdPaint: Paint,
        animatedValues: List<Float>
    ) {
        val animatedValue1 = animatedValues[0]
        val animatedValue2 = animatedValues[1]

        canvas.showProgress(animatedValue1._2dec, animatedValue2._2dec)

        val originalShape = canvas.halfFrame()
        val createdShape = originalShape
            .pointAtAnchor(E.point(animatedValue1, animatedValue2))

        canvas.draw(originalShape, originalPaint)
        canvas.draw(createdShape, 8.dp, createdPaint)
    }

}


object RectAlignmentAnchor_Rect : DemoRunner() {

    override val progressLabels: List<String> = listOf("x", "y", "size")

    override fun demo(
        canvas: Canvas,
        originalPaint: Paint,
        createdPaint: Paint,
        animatedValues: List<Float>
    ) {
        val x = animatedValues[0].lerp(-0.5, 1.5)
        val y = animatedValues[1].lerp(-0.5, 1.5)
        val size = animatedValues[2].lerp(16.dp, 128.dp)

        canvas.showProgress(x._2dec, y._2dec, size)

        val originalShape = canvas.halfFrame()
        val createdShape = E.mrectCenter(
            center = originalShape.pointAtAnchor(E.point(x, y)),
            size = E.sizeSquare(size)
        )

        canvas.draw(originalShape, originalPaint)
        canvas.draw(createdShape, createdPaint)
    }

}

