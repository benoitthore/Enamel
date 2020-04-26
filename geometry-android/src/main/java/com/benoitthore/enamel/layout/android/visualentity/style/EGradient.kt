package com.benoitthore.enamel.layout.android.visualentity.style

import android.graphics.LinearGradient
import android.graphics.RadialGradient
import android.graphics.Shader
import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ELine
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.primitives.EAngle

sealed class EGradient(
    protected val colors: List<Int>,
    protected val stops: List<Float>?,
    protected val shaderMode: Shader.TileMode
) {
    companion object {
        val DEFAULT_SHADER_MODE = Shader.TileMode.CLAMP
    }
    abstract val shader : Shader

    class Line(
        val line: ELine,
        colors: List<Int>,
        stops: List<Float>? = null,
        shaderMode: Shader.TileMode = DEFAULT_SHADER_MODE
    ) : EGradient(colors, stops, shaderMode) {

        override val shader: LinearGradient by lazy {
            line.toLinearGradient(colors, stops, shaderMode)
        }
    }

    class Radial(
        val circle: ECircle,
        colors: List<Int>,
        stops: List<Float>? = null,
        shaderMode: Shader.TileMode = DEFAULT_SHADER_MODE
    ) : EGradient(colors, stops, shaderMode) {
        override val shader: RadialGradient by lazy {
            circle.toRadialGradient(colors = colors, stops = stops, shaderMode = shaderMode)
        }
    }
}

fun ELine.toEGradient(
    colors: List<Int>,
    stops: List<Float>? = null,
    shaderMode: Shader.TileMode = EGradient.DEFAULT_SHADER_MODE
) = EGradient.Line(
    line = this,
    colors = colors,
    stops = stops,
    shaderMode = shaderMode
)

fun ELine.toLinearGradient(vararg colors: Int) = toLinearGradient(colors.toList())

fun ELine.toLinearGradient(
    colors: List<Int>,
    stops: List<Float>? = null,
    shaderMode: Shader.TileMode = EGradient.DEFAULT_SHADER_MODE
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
    shaderMode: Shader.TileMode = EGradient.DEFAULT_SHADER_MODE
) = RadialGradient(
    center.x,
    center.y,
    radius,
    colors.toIntArray(),
    stops?.toFloatArray(),
    shaderMode
)