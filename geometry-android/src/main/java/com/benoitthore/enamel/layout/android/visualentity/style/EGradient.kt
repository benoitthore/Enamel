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

    abstract fun getShader(frame: ERect): Shader

    class DiagonalConstrained(
        val line: ELine,
        colors: List<Int>,
        stops: List<Float>? = null,
        shaderMode: Shader.TileMode = DEFAULT_SHADER_MODE
    ) : EGradient(colors, stops, shaderMode) {

        private val shader: LinearGradient by lazy {
            line.toLinearGradient(colors, stops, shaderMode)
        }

        override fun getShader(frame: ERect): LinearGradient = shader
    }

    class DiagonalFill(
        val angle: EAngle,
        colors: List<Int>,
        stops: List<Float>? = null,
        shaderMode: Shader.TileMode = DEFAULT_SHADER_MODE
    ) : EGradient(colors, stops, shaderMode) {
        override fun getShader(frame: ERect): LinearGradient {
            TODO("Implement when this is done: https://trello.com/c/Db0MDiJb/12-add-rect-to-line-and-line-to-rect-functions")
//            val center = frame.center()
//            // TODO
//            val line = center line center.offsetAngle(angle, 10)
//            return line.toLinearGradient(colors)
        }
    }

    class Radial(
        val circle: ECircle,
        colors: List<Int>,
        stops: List<Float>? = null,
        shaderMode: Shader.TileMode = DEFAULT_SHADER_MODE
    ) : EGradient(colors, stops, shaderMode) {
        private val shader: RadialGradient by lazy {
            circle.toRadialGradient(colors = colors, stops = stops, shaderMode = shaderMode)
        }

        override fun getShader(frame: ERect): Shader = shader
    }
}

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