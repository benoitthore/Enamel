package com.benoitthore.enamel.layout.android.visualentity.style

import android.graphics.Shader
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.outterRect

class EShader(val shader: Shader, val frame: ERect)

fun ELine.toShader(vararg colors: Int) = toShader(colors.toList())
fun ECircle.toShader(vararg colors: Int) = toShader(colors.toList())

fun ELine.toShader(
    colors: List<Int>,
    stops: List<Float>? = null,
    shaderMode: Shader.TileMode = Shader.TileMode.REPEAT
) =
    EShader(
        toLinearGradient(colors, stops, shaderMode),
        E.RectMutableCorners(start, end)
    )

fun ECircle.toShader(
    colors: List<Int>,
    stops: List<Float>? = null,
    shaderMode: Shader.TileMode = Shader.TileMode.REPEAT
) = EShader(toRadialGradient(colors, stops, shaderMode), outterRect())