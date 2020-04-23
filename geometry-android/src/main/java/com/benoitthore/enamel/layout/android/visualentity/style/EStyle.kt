package com.benoitthore.enamel.layout.android.visualentity.style

import android.graphics.Shader
import com.benoitthore.enamel.geometry.figures.ECircle
import com.benoitthore.enamel.geometry.figures.ELine
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
        constructor(color: Int, width: Float) : this(Mesh.Color(color), width)
    }

    sealed class Mesh(var alpha: Float) {
        class Color(var color: Int, alpha: Float = 1f) : Mesh(alpha)
        class Gradient(var gradient: EGradient, alpha: Float = 1f) : Mesh(alpha)
    }

}
