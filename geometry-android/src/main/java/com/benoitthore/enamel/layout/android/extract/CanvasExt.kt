package com.benoitthore.enamel.layout.android.extract

import android.graphics.*
import android.os.Build
import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.layout.android.visualentity.set

fun Canvas.drawPointList(points: List<EPoint>, radius: Float, paint: Paint) =
    points.forEach { draw(it, radius, paint) }

fun Canvas.draw(p: EPoint, radius: Float, paint: Paint) {
    drawCircle(p.x, p.y, radius, paint)
}

//private val targetRectF = RectF()
fun Canvas.drawCircleList(circles: List<ECircle>, paint: Paint) =
    circles.forEach { draw(it, paint) }

fun Canvas.draw(circle: ECircle, paint: Paint) {
    drawCircle(circle.x, circle.y, circle.radius, paint)
}

fun Canvas.draw(line: ELine, paint: Paint) = draw(line.start, line.end, paint)

fun Canvas.draw(start: EPoint, end: EPoint, paint: Paint) {
    drawLine(start.x, start.y, end.x, end.y, paint)
}

fun Canvas.drawRectList(rects: List<ERect>, paint: Paint) = rects.forEach { draw(it, paint) }
fun Canvas.draw(rect: ERect, paint: Paint) {
    drawRect(
        rect.left,
        rect.top,
        rect.right,
        rect.bottom, paint
    )
}

fun Canvas.drawRoundRectList(rects: List<ERect>, rx: Number, ry: Number, paint: Paint) =
    rects.forEach { draw(it, rx, ry, paint) }

fun Canvas.draw(rect: ERect, rx: Number, ry: Number, paint: Paint) {
//    synchronized(targetRectF) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        drawRoundRect(
            rect.left,
            rect.top,
            rect.right,
            rect.bottom,
            rx.toFloat(),
            ry.toFloat(),
            paint
        )
    } else {
        drawRoundRect(RectF().set(rect), rx.toFloat(), ry.toFloat(), paint)
    }
//    }
}