package com.thorebenoit.enamel.kotlin.geometry.alignement

import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointImmutable


class NamedPoint {
    companion object {
        val topLeft = allocate { EPointImmutable(x = 0, y = 0) }
        val topCenter = allocate { EPointImmutable(x = 0.5, y = 0) }
        val topRight = allocate { EPointImmutable(x = 1, y = 0) }

        val middleLeft = allocate { EPointImmutable(x = 0, y = 0.5) }
        val center = allocate { EPointImmutable(x = 0.5, y = 0.5) }
        val middleRight = allocate { EPointImmutable(x = 1, y = 0.5) }

        val bottomLeft = allocate { EPointImmutable(x = 0, y = 1) }
        val bottomCenter = allocate { EPointImmutable(x = 0.5, y = 1) }
        val bottomRight = allocate { EPointImmutable(x = 1, y = 1) }

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