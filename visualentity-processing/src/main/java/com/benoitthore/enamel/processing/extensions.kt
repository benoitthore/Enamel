package com.benoitthore.enamel.processing

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.size.ESize
import processing.core.PApplet
import processing.core.PConstants


fun PApplet.translate(vector: EPoint) {
    translate(vector.x, vector.y)
}

fun PApplet.rotate(angle: EAngle, center: EPoint? = null) {
    if (center != null) {
        translate(center.x, center.y)
    }
    rotate(angle.degrees)
    if (center != null) {
        translate(-center.x, -center.y)
    }
}

inline fun <T : PApplet> T.pushPop(block: T.() -> Unit) {
    pushMatrix()
    pushStyle()
    block()
    popMatrix()
    popStyle()
}

fun PApplet.mousePosition(target: EPoint = E.Point()) = target.set(mouseX, mouseY)
fun PApplet.getViewSize(target: ESize = E.Size()) = target.apply {
    width = pixelWidth.f
    height = pixelHeight.f
}

fun PApplet.getViewCenter(point: EPoint=E.Point()) = point.set(width / 2f, height / 2f)

fun PApplet.getViewBounds(target: ERect = E.Rect()) =
    target.apply { _setBounds(0, 0, this@getViewBounds.width, this@getViewBounds.height) }

fun ERect.setBounds(applet: PApplet) = apply { applet.getViewBounds(target = this) }

fun PApplet.draw(line: ELine) {
    pushPop {
        with(line) {
            line(x1, y1, x2, y2)
        }
    }
}

fun PApplet.draw(circle: ECircle) {
    pushPop {
        with(circle) {
            ellipseMode(PConstants.CENTER)
            ellipse(centerX, centerY, radius * 2, radius * 2)
        }
    }
}

fun PApplet.draw(oval: EOval) {
    pushPop {
        with(oval) {
            ellipseMode(PConstants.CENTER)
            ellipse(centerX, centerY, rx * 2, ry * 2)
        }
    }
}

fun PApplet.draw(rect: ERect) {
    pushPop {
        with(rect) {
            rectMode(PConstants.CORNER)
            rect(originX, originY, width, height)
        }
    }
}


//fun EShader.toAndroid(): Shader = when (val shaderType = shaderType) {
//    is EShader.ShaderType.Radial -> RadialGradient(
//        shaderType.circle.x,
//        shaderType.circle.y,
//        shaderType.circle.radius,
//        colors.toIntArray(),
//        stops?.toFloatArray(),
//        shaderMode.toAndroid()
//    )
//    is EShader.ShaderType.Linear -> LinearGradient(
//        shaderType.line.start.x,
//        shaderType.line.start.y,
//        shaderType.line.end.x,
//        shaderType.line.end.y,
//        colors.toIntArray(),
//        stops?.toFloatArray(),
//        shaderMode.toAndroid()
//    )
//}
//
//fun EShader.ShaderTileMode.toAndroid(): Shader.TileMode = when (this) {
//    EShader.ShaderTileMode.CLAMP -> Shader.TileMode.CLAMP
//    EShader.ShaderTileMode.REPEAT -> Shader.TileMode.REPEAT
//    EShader.ShaderTileMode.MIRROR -> Shader.TileMode.MIRROR
//}
