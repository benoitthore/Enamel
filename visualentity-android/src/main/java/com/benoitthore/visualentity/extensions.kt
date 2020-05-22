package com.benoitthore.visualentity

import android.graphics.*
import com.benoitthore.enamel.layout.android.withTransform
import com.benoitthore.visualentity.style.EShader
import com.benoitthore.visualentity.style.Mesh

internal fun Paint.setMesh(mesh: Mesh?) {
    reset()
    mesh?.color?.let { color = it }
    mesh?.shader?.toAndroid()?.let { shader = it }
}

fun EShader.toAndroid(): Shader = when (val shaderType = shaderType) {
    is EShader.ShaderType.Radial -> RadialGradient(
        shaderType.circle.x,
        shaderType.circle.y,
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
