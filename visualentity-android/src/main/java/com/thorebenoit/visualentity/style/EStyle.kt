package com.thorebenoit.visualentity.style

import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

interface EStyleable {
    var style: EStyle
}

data class EStyle(
    val fill: Mesh? = null,
    val border: Border? = null,
    val shadow: Shadow? = null
) {
    data class Shadow(val mesh: Mesh, val position: EPointMutable)
    data class Border(val mesh: Mesh, var width: Float) {
        constructor(color: Int, width: Float) : this(Mesh(color = color), width)
    }
}

class Mesh(
    var color: Int? = null,
    val shader: EShader? = null
)