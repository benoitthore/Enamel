package com.benoitthore.enamel.layout.android

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Region
import android.os.Build
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.layout.android.visualentity.set
import com.benoitthore.enamel.layout.android.visualentity.withSave

fun Canvas.drawPointList(points: List<EPoint>, radius: Float, paint: Paint) =
    points.forEach { draw(it, radius, paint) }

fun Canvas.draw(p: EPoint, radius: Float, paint: Paint) {
    drawCircle(p.x, p.y, radius, paint)
}

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
fun Canvas.draw(rect: ERect, paint: Paint, translate: Boolean = true) {
    if (translate) {
        withSave {
            translate(rect.origin)
            drawRect(0f, 0f, rect.width, rect.height, paint)
        }
    } else {
        drawRect(
            rect.left,
            rect.top,
            rect.right,
            rect.bottom, paint
        )
    }
}

fun Canvas.drawRoundRectList(rects: List<ERect>, rx: Number, ry: Number, paint: Paint) =
    rects.forEach { draw(it, rx, ry, paint) }

fun Canvas.draw(rect: ERect, rx: Number, ry: Number, paint: Paint) {
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
}

fun Canvas.translate(xy: Tuple2) {
    translate(xy.v1.toFloat(), xy.v2.toFloat())
}

fun Canvas.scale(xy: Tuple2) {
    scale(xy.v1.toFloat(), xy.v2.toFloat())
}

fun Canvas.skew(xy: Tuple2) {
    skew(xy.v1.toFloat(), xy.v2.toFloat())
}

fun Canvas.clipPath(path: PathSVGContext) {
    clipPath(path.path)
}

fun Canvas.clipOutPath(path: PathSVGContext) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        clipOutPath(path.path)
    } else {
        clipPath(path.path, Region.Op.DIFFERENCE)
    }
}