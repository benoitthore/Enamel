package com.benoitthore.enamel.geometry.alignement

import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.builders.E


class NamedPoint {
    companion object {
        val topLeft = allocate { E.Point(x = 0, y = 0) }
        val topCenter = allocate { E.Point(x = 0.5, y = 0) }
        val topRight = allocate { E.Point(x = 1, y = 0) }

        val centerLeft = allocate { E.Point(x = 0, y = 0.5) }
        val center = allocate { E.Point(x = 0.5, y = 0.5) }
        val centerRight = allocate { E.Point(x = 1, y = 0.5) }

        val bottomLeft = allocate { E.Point(x = 0, y = 1) }
        val bottomCenter = allocate { E.Point(x = 0.5, y = 1) }
        val bottomRight = allocate { E.Point(x = 1, y = 1) }

        val vertices = listOf(topLeft, topRight, bottomRight, bottomLeft)
        val all = listOf(
            topLeft,
            topCenter,
            topRight,

            centerLeft,
            center,
            centerRight,

            bottomLeft,
            bottomCenter,
            bottomRight
        )
    }
}