package com.benoitthore.enamel.geometry.alignement

import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.builders.E


class NamedPoint {
    companion object {
        val topLeft = allocate { E.point(x = 0, y = 0) }
        val topCenter = allocate { E.point(x = 0.5, y = 0) }
        val topRight = allocate { E.point(x = 1, y = 0) }

        val middleLeft = allocate { E.point(x = 0, y = 0.5) }
        val center = allocate { E.point(x = 0.5, y = 0.5) }
        val middleRight = allocate { E.point(x = 1, y = 0.5) }

        val bottomLeft = allocate { E.point(x = 0, y = 1) }
        val bottomCenter = allocate { E.point(x = 0.5, y = 1) }
        val bottomRight = allocate { E.point(x = 1, y = 1) }

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