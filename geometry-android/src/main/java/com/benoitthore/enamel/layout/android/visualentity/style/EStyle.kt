package com.benoitthore.enamel.layout.android.visualentity.style

import android.graphics.Shader
import com.benoitthore.enamel.geometry.primitives.*

interface EStyleable {
    var style: EStyle
}

data class EStyle(
    var fill: Mesh? = null,
    var border: Border? = null,
    var shadow: Shadow? = null
) {
    data class Shadow(var mesh: Mesh, val position: EPointMutable)
    data class Border(var mesh: Mesh, var width: Float) {
        constructor(color: Int, width: Float) : this(Mesh(color = color), width)
    }
}

class Mesh(
    var color: Int? = null,
    val shader: Shader? = null
)