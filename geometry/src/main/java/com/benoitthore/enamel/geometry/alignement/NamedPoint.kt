package com.benoitthore.enamel.geometry.alignement

import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.primitives.EPoint


class NamedPoint {
    companion object {
        val topLeft = allocate { EPoint(x = 0, y = 0) }
        val topCenter = allocate { EPoint(x = 0.5, y = 0) }
        val topRight = allocate { EPoint(x = 1, y = 0) }

        val middleLeft = allocate { EPoint(x = 0, y = 0.5) }
        val center = allocate { EPoint(x = 0.5, y = 0.5) }
        val middleRight = allocate { EPoint(x = 1, y = 0.5) }

        val bottomLeft = allocate { EPoint(x = 0, y = 1) }
        val bottomCenter = allocate { EPoint(x = 0.5, y = 1) }
        val bottomRight = allocate { EPoint(x = 1, y = 1) }

        val vertices = listOf(topLeft, topRight, bottomRight, bottomLeft)
        val all = listOf(
            topLeft,
            topCenter,
            topRight,

            middleLeft,
            center,
            middleRight,

            bottomLeft,
            bottomCenter,
            bottomRight
        )
    }
}