package com.thorebenoit.enamel.kotlin.geometry.alignement

import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType


class NamedPoint {
    companion object {
        val topLeft = allocate { EPointType(x = 0, y = 0) }
        val topCenter = allocate { EPointType(x = 0.5, y = 0) }
        val topRight = allocate { EPointType(x = 1, y = 0) }

        val middleLeft = allocate { EPointType(x = 0, y = 0.5) }
        val center = allocate { EPointType(x = 0.5, y = 0.5) }
        val middleRight = allocate { EPointType(x = 1, y = 0.5) }

        val bottomLeft = allocate { EPointType(x = 0, y = 1) }
        val bottomCenter = allocate { EPointType(x = 0.5, y = 1) }
        val bottomRight = allocate { EPointType(x = 1, y = 1) }

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