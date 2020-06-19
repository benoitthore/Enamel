package com.benoitthore.visualentity.android.utils

import android.graphics.*
import com.benoitthore.visualentity.style.EShader
import com.benoitthore.visualentity.style.EMesh

internal fun Paint.setMesh(mesh: EMesh?) {
    reset()
    mesh?.color?.let { color = it }
    mesh?.shader?.toAndroid()?.let { shader = it }
}

fun EShader.toAndroid(): Shader = when (val shaderType = shaderType) {
    is EShader.ShaderType.Radial -> RadialGradient(
        shaderType.circle.centerX,
        shaderType.circle.centerY,
        shaderType.circle.radius,
        colors.toIntArray(),
        stops?.toFloatArray(),
        shaderMode.toAndroid()
    )
    is EShader.ShaderType.Linear -> LinearGradient(
        shaderType.line.start.x,
        shaderType.line.start.y,
        shaderType.line.end.x,
        shaderType.line.end.y,
        colors.toIntArray(),
        stops?.toFloatArray(),
        shaderMode.toAndroid()
    )
}

fun EShader.ShaderTileMode.toAndroid(): Shader.TileMode = when (this) {
    EShader.ShaderTileMode.CLAMP -> Shader.TileMode.CLAMP
    EShader.ShaderTileMode.REPEAT -> Shader.TileMode.REPEAT
    EShader.ShaderTileMode.MIRROR -> Shader.TileMode.MIRROR
}
