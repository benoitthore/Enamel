package com.benoitthore.enamel.android.demo

import android.graphics.Canvas
import android.graphics.Paint
import com.benoitthore.enamel.android.dp
import com.benoitthore.enamel.core._2dec
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.core.math.lerp
import com.benoitthore.enamel.geometry.alignement.*
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.interfaces.bounds.pointAtAnchor
import com.benoitthore.enamel.geometry.primitives.angle.rotations
import com.benoitthore.enamel.geometry.toListOfPoint
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.enamel.layout.android.drawPointList

val Demos = mutableListOf(
    CircleToListOfPoint,
    RectAlignmentAnchor_Point,
    RectAlignmentAnchor_Rect,
    RectAlignmentInside,
    RectAlignmentOutside
)

object CircleToListOfPoint : DemoDrawer() {

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


object RectAlignmentOutside : DemoDrawer() {

    override val progressLabels: List<String> = listOf("alignment", "size")

    override fun demo(
        canvas: Canvas,
        originalPaint: Paint,
        createdPaint: Paint,
        animatedValues: List<Float>
    ) {
        val progress1 = EAlignment.values().toList()[animatedValues[0]]
        val progress2 = animatedValues[1].lerp(32.dp, 100.dp)

        canvas.showProgress(progress1, progress2)

        val originalShape = canvas.halfFrame()
        val createdShape = E.CircleMutable(radius = progress2)
            .selfAlignOutside(originalShape, alignment = progress1, spacing = 16.dp)

        canvas.draw(originalShape, originalPaint)
        canvas.draw(createdShape, createdPaint)
    }

}

object RectAlignmentInside : DemoDrawer() {

    override val progressLabels: List<String> = listOf("alignment", "size")

    override fun demo(
        canvas: Canvas,
        originalPaint: Paint,
        createdPaint: Paint,
        animatedValues: List<Float>
    ) {
        val progress1 = EAlignment.values().toList()[animatedValues[0]]
        val progress2 = animatedValues[1].lerp(32.dp, 100.dp)

        canvas.showProgress(progress1, progress2)

        val originalShape = canvas.halfFrame()
        val createdShape =
            E.CircleMutable(radius = progress2)
            .selfAlignInside(originalShape, alignment = progress1)

        canvas.draw(originalShape, originalPaint)
        canvas.draw(createdShape, createdPaint)
    }

}


object RectAlignmentAnchor_Point : DemoDrawer() {

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
            .pointAtAnchor(E.Point(animatedValue1, animatedValue2))

        canvas.draw(originalShape, originalPaint)
        canvas.draw(createdShape, 8.dp, createdPaint)
    }

}


object RectAlignmentAnchor_Rect : DemoDrawer() {

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
        val createdShape = E.RectMutableCenter(
            center = originalShape.pointAtAnchor(E.Point(x, y)),
            size = E.SizeSquare(size)
        )

        canvas.draw(originalShape, originalPaint)
        canvas.draw(createdShape, createdPaint)
    }

}

