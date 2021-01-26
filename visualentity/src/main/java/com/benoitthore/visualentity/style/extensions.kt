package com.benoitthore.visualentity.style

import com.benoitthore.enamel.geometry.alignement.copy
import com.benoitthore.enamel.geometry.builders.RectCorners
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.functions.setOriginSize
import com.benoitthore.enamel.geometry.functions.outerRect
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.point


fun EMesh.toBorder(width: Number) = EStyle.Border(this, width.toFloat())

fun EMesh.toShadow(n: Number) = toShadow(n point n)
fun EMesh.toShadow(xOff: Number, yOff: Number) = toShadow(xOff point yOff)
fun EMesh.toShadow(offset: EPoint) = EStyle.Shadow(this, offset)

fun ELine.toShader(vararg colors: Int, resetOrigin: Boolean = false) =
    toShader(colors.toList(), resetOrigin = resetOrigin)

fun ECircle.toShader(vararg colors: Int, resetOrigin: Boolean = false) =
    toShader(colors.toList(), resetOrigin = resetOrigin)

fun ELine.toShader(
    colors: List<Int>,
    stops: List<Float>? = null,
    shaderMode: EShader.ShaderTileMode = EShader.ShaderTileMode.CLAMP,
    resetOrigin: Boolean = false
) =
    EShader(
        shaderType = EShader.ShaderType.Linear(
            toMutable().apply {
                if (resetOrigin) {
                    this.setOriginSize(0, 0)
                }
            }
        ),
        shaderMode = shaderMode,
        colors = colors,
        stops = stops,
        frame = RectCorners(start, end)
    )

fun ECircle.toShader(
    colors: List<Int>,
    stops: List<Float>? = null,
    shaderMode: EShader.ShaderTileMode = EShader.ShaderTileMode.CLAMP,
    resetOrigin: Boolean = false
) =
    EShader(
        shaderType = EShader.ShaderType.Radial(
            toMutable().apply {
                if (resetOrigin) {
                    this.setOriginSize(0, 0)
                }
            }
        ),
        shaderMode = shaderMode,
        colors = colors,
        stops = stops,
        frame = outerRect()
    )