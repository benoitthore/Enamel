package com.benoitthore.enamel.processing

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import processing.core.PApplet
import processing.core.PConstants

fun PApplet.mousePosition(target: EPointMutable = E.PointMutable()) = target.set(mouseX, mouseY)
fun PApplet.getBounds(target: ERectMutable = E.RectMutable()) = target.apply {
    target.setBounds(0, 0, this@getBounds.width, this@getBounds.height)
}

fun PApplet.draw(rect: ERect) {
    with(rect) {
        rectMode(PConstants.CORNER)
        rect(originX, originY, width, height)
    }
}

fun PApplet.draw(line: ELine) {
    with(line) {
        line(x1, y1, x2, y2)
    }
}

fun PApplet.draw(circle: ECircle) {
    with(circle) {
        ellipse(x, y, radius * 2, radius * 2)
    }
}

fun PApplet.draw(oval: EOval) {
    with(oval) {
        ellipse(y, x, rx, ry)
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
