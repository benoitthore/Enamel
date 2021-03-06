package com.benoitthore.enamel.visualentity.android.utils

import android.content.res.Resources
import android.graphics.*
import com.benoitthore.visualentity.style.EShader
import com.benoitthore.visualentity.style.EMesh

val Number.dp get() = Resources.getSystem().displayMetrics.density * toFloat()

internal fun Paint.setMesh(mesh: EMesh?, stroke : Boolean = false) {
    reset()
    mesh?.color?.let { color = it }
    mesh?.shader?.toAndroid()?.let { shader = it }
    isAntiAlias = true
    if(stroke){
        style = Paint.Style.STROKE
    }
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
