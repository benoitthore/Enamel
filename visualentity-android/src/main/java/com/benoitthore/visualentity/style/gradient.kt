package com.benoitthore.visualentity.style

import android.graphics.LinearGradient
import android.graphics.RadialGradient
import android.graphics.Shader
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine

fun ELine.toLinearGradient(vararg colors: Int) = toLinearGradient(colors.toList())
fun ECircle.toRadialGradient(vararg colors: Int) = toRadialGradient(colors.toList())

fun ELine.toLinearGradient(
    colors: List<Int>,
    stops: List<Float>? = null,
    shaderMode: Shader.TileMode = Shader.TileMode.REPEAT
) =
    LinearGradient(
        start.x,
        start.y,
        end.x,
        end.y,
        colors.toIntArray(),
        stops?.toFloatArray(),
        shaderMode
    )

fun ECircle.toRadialGradient(
    colors: List<Int>,
    stops: List<Float>? = null,
    shaderMode: Shader.TileMode = Shader.TileMode.REPEAT
) = RadialGradient(
    center.x,
    center.y,
    radius,
    colors.toIntArray(),
    stops?.toFloatArray(),
    shaderMode
)