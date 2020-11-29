package com.benoitthore.enamel.geometry.alignement

import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.builders.*


class NamedPoint {
    companion object {
        val topLeft = allocate { Point(x = 0, y = 0) }
        val topCenter = allocate { Point(x = 0.5, y = 0) }
        val topRight = allocate { Point(x = 1, y = 0) }

        val centerLeft = allocate { Point(x = 0, y = 0.5) }
        val center = allocate { Point(x = 0.5, y = 0.5) }
        val centerRight = allocate { Point(x = 1, y = 0.5) }

        val bottomLeft = allocate { Point(x = 0, y = 1) }
        val bottomCenter = allocate { Point(x = 0.5, y = 1) }
        val bottomRight = allocate { Point(x = 1, y = 1) }

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