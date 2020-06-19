package com.benoitthore.enamel.layout.android

import android.graphics.*
import android.os.Build
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform

fun Canvas.drawPointList(points: List<EPoint>, radius: Float, paint: Paint) =
    points.forEach { draw(it, radius, paint) }

fun Canvas.draw(circle: ECircle, paint: Paint) {
    draw(circle.center, circle.radius, paint)
}

fun Canvas.draw(oval: EOval, paint: Paint) {
    withSave {
        with(oval) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawOval(left, top, right, bottom, paint)
            } else {
                drawOval(RectF(left, top, right, bottom), paint)
            }
        }
    }
}

fun Canvas.draw(p: EPoint, radius: Float, paint: Paint) {
    withSave {
        translate(p.x, p.y)
        drawCircle(0f, 0f, radius, paint)
    }
}

fun Canvas.drawCircleList(circles: List<ECircle>, paint: Paint) =
    circles.forEach { draw(it, paint) }


fun Canvas.draw(line: ELine, paint: Paint) = draw(line.start, line.end, paint)

fun Canvas.draw(start: EPoint, end: EPoint, paint: Paint) {
    withSave {
        translate(start.x, start.y)
        drawLine(0f, 0f, end.x - start.x, end.y - start.y, paint)
    }
}

fun Canvas.draw(rect: ERect, paint: Paint) {
    withSave {
        translate(rect.origin)
        drawRect(0f, 0f, rect.width, rect.height, paint)
    }
}

fun Canvas.drawRectList(rects: List<ERect>, paint: Paint) = rects.forEach { draw(it, paint) }

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

fun Canvas.drawRoundRectList(rects: List<ERect>, rx: Number, ry: Number, paint: Paint) =
    rects.forEach { draw(it, rx, ry, paint) }


fun Canvas.translate(xy: Tuple2) = translate(xy.v1.toFloat(), xy.v2.toFloat())

fun Canvas.scale(xy: Tuple2) = scale(xy.v1.toFloat(), xy.v2.toFloat())

fun Canvas.skew(xy: Tuple2) = skew(xy.v1.toFloat(), xy.v2.toFloat())

fun Canvas.clipPath(path: PathSVGContext) = clipPath(path.path)

fun Canvas.clipOutPath(path: PathSVGContext): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        clipOutPath(path.path)
    } else {
        clipPath(path.path, Region.Op.DIFFERENCE)
    }
}

fun Canvas.clipRect(rect: ERectMutable) {
    clipRect(
        rect.left,
        rect.top,
        rect.right,
        rect.bottom
    )
}

fun Canvas.drawBackground(paint: Paint) {
    drawRect(0f, 0f, width.f, height.f, paint)
}


fun Rect.set(rect: ERect) = apply {
    left = rect.left.toInt()
    top = rect.top.toInt()
    right = rect.right.toInt()
    bottom = rect.bottom.toInt()
}

fun RectF.set(rect: ERect) = apply {
    left = rect.left
    top = rect.top
    right = rect.right
    bottom = rect.bottom
}

inline fun Canvas.withPathMeasureData(
    data: EPathMeasure.Data,
    crossinline block: Canvas.() -> Unit
) = withSave {
    translate(data.position.x, data.position.y)
    rotate(data.angle.degrees)
    block()
}

inline fun Canvas.withTranslation(
    translation: EPoint,
    crossinline block: Canvas.() -> Unit
) = withSave {
    translate(translation.x, translation.y)
    block()
}

inline fun Canvas.withRotation(
    angle: EAngle,
    pivot: EPoint? = null,
    crossinline block: Canvas.() -> Unit
) = withSave {
    if (pivot == null) {
        rotate(angle.degrees)
    } else {
        rotate(angle.degrees, pivot.x, pivot.y)
    }
    block()
}

inline fun Canvas.withScale(
    scale: EPoint,
    pivot: EPoint? = null,
    crossinline block: Canvas.() -> Unit
) = withSave {
    if (pivot == null) {
        scale(scale.x, scale.y)
    } else {
        scale(scale.x, scale.y, pivot.x, pivot.y)
    }
    block()
}


inline fun Canvas.withTransform(
    transform: ETransform,
    crossinline block: Canvas.() -> Unit
) {
    with(transform) {
        withTranslation(translation) {
            withRotation(rotation, rotationPivot) {
                withScale(scale, scalePivot) {
                    block()
                }
            }
        }
    }
}

inline fun Canvas.withSave(crossinline block: Canvas.() -> Unit) = apply {
    val save = save()
    runCatching { block() }
    restoreToCount(save)
}