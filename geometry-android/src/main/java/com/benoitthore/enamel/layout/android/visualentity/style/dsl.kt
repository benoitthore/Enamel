package com.benoitthore.enamel.layout.android.visualentity.style

import android.graphics.Shader
import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.primitives.EAngle
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.geometry.primitives.point


fun EStyle.Mesh.asBorder(width: Number) = EStyle.Border(this, width.toFloat())
fun EStyle.Mesh.asShadow(n: Number) = asShadow(n point n)
fun EStyle.Mesh.asShadow(xOff: Number, yOff: Number) = asShadow(xOff point yOff)
fun EStyle.Mesh.asShadow(offset: EPoint) = asShadow(offset.toMutable())
fun EStyle.Mesh.asShadow(offset: EPointMutable) = EStyle.Shadow(this, offset)
fun EGradient.asMesh(alpha: Float = 1f) = EStyle.Mesh.Gradient(this, alpha)
fun EGradient.asBorder(width: Number, alpha: Float = 1f) = asMesh(alpha).asBorder(width)


interface MeshBuilder {
    fun color(color: Int, alpha: Number = 1) =
        EStyle.Mesh.Color(color = color, alpha = alpha.toFloat())
}

// TODO This ins't easy to use, refactor it
//interface GradientBuilder {
//    fun diagonalConstrained(
//        line: ELine,
//        colors: List<Int>,
//        stops: List<Float>? = null,
//        shaderMode: Shader.TileMode = EGradient.DEFAULT_SHADER_MODE
//    ) = EGradient.DiagonalConstrained(
//        line = line,
//        colors = colors,
//        stops = stops,
//        shaderMode = shaderMode
//    )
//
//    fun diagonalFill(
//        angle: EAngle,
//        colors: List<Int>,
//        stops: List<Float>? = null,
//        shaderMode: Shader.TileMode = EGradient.DEFAULT_SHADER_MODE
//    ) = EGradient.DiagonalFill(
//        angle = angle,
//        colors = colors,
//        stops = stops,
//        shaderMode = shaderMode
//    )
//
//    fun radial(
//        circle: ECircle,
//        colors: List<Int>,
//        stops: List<Float>? = null,
//        shaderMode: Shader.TileMode = EGradient.DEFAULT_SHADER_MODE
//    ) = EGradient.Radial(
//        circle = circle,
//        colors = colors,
//        stops = stops,
//        shaderMode = shaderMode
//    )
//
//
//    fun diagonalConstrainedMesh(
//        line: ELine,
//        colors: List<Int>,
//        stops: List<Float>? = null,
//        shaderMode: Shader.TileMode = EGradient.DEFAULT_SHADER_MODE
//    ) = diagonalConstrained(
//        line = line,
//        colors = colors,
//        stops = stops,
//        shaderMode = shaderMode
//    ).asMesh()
//
//    fun diagonalFillMesh(
//        angle: EAngle,
//        colors: List<Int>,
//        stops: List<Float>? = null,
//        shaderMode: Shader.TileMode = EGradient.DEFAULT_SHADER_MODE
//    ) = diagonalFill(
//        angle = angle,
//        colors = colors,
//        stops = stops,
//        shaderMode = shaderMode
//    ).asMesh()
//
//    fun radialMesh(
//        circle: ECircle,
//        colors: List<Int>,
//        stops: List<Float>? = null,
//        shaderMode: Shader.TileMode = EGradient.DEFAULT_SHADER_MODE
//    ) = radial(
//        circle = circle,
//        colors = colors,
//        stops = stops,
//        shaderMode = shaderMode
//    ).asMesh()
//
//}
