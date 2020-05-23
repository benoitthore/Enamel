package com.benoitthore.visualentity.style

import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

interface EStyleable {
    var style: EStyle
}

data class EStyle(
    val fill: EMesh? = null,
    val border: Border? = null,
    val shadow: Shadow? = null
) {
    data class Shadow(val mesh: EMesh, val position: EPointMutable)
    data class Border(val mesh: EMesh, var width: Float) {
        constructor(color: Int, width: Float) : this(EMesh(color = color), width)
    }
}

data class EMesh(
    var color: Int? = null,
    val shader: EShader? = null
)