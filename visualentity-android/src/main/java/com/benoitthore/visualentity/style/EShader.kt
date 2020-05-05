package com.benoitthore.visualentity.style

import android.graphics.Shader
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.ELine
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.interfaces.bounds.setOriginSize
import com.benoitthore.enamel.geometry.outterRect
import com.benoitthore.enamel.geometry.toMutable

class EShader(val shader: Shader, val frame: ERect)

fun ELine.toShader(vararg colors: Int, resetOrigin: Boolean = false) =
    toShader(colors.toList(), resetOrigin = resetOrigin)

fun ECircle.toShader(vararg colors: Int, resetOrigin: Boolean = false) =
    toShader(colors.toList(), resetOrigin = resetOrigin)

fun ELine.toShader(
    colors: List<Int>,
    stops: List<Float>? = null,
    shaderMode: Shader.TileMode = Shader.TileMode.REPEAT,
    resetOrigin: Boolean = false
) =
    EShader(
        toMutable().apply {
            if (resetOrigin) {
                setOriginSize(0, 0)
            }
        }
            .toLinearGradient(colors, stops, shaderMode),
        E.RectMutableCorners(start, end)
    )

fun ECircle.toShader(
    colors: List<Int>,
    stops: List<Float>? = null,
    shaderMode: Shader.TileMode = Shader.TileMode.REPEAT,
    resetOrigin: Boolean = false
) = EShader(
    toMutable().apply {
        if (resetOrigin) {
            setOriginSize(0, 0)
        }
    }
        .toRadialGradient(colors, stops, shaderMode), outterRect()
)