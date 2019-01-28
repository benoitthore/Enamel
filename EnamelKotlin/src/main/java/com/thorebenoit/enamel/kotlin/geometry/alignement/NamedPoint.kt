package com.thorebenoit.enamel.kotlin.geometry.alignement

import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointImmutable


class NamedPoint {
    companion object {
        val topLeft = EPointImmutable(x = 0, y = 0)
        val topCenter = EPointImmutable(x = 0.5, y = 0)
        val topRight = EPointImmutable(x = 1, y = 0)

        val middleLeft = EPointImmutable(x = 0, y = 0.5)
        val center = EPointImmutable(x = 0.5, y = 0.5)
        val middleRight = EPointImmutable(x = 1, y = 0.5)

        val bottomLeft = EPointImmutable(x = 0, y = 1)
        val bottomCenter = EPointImmutable(x = 0.5, y = 1)
        val bottomRight = EPointImmutable(x = 1, y = 1)

        val vertices = listOf(topLeft, topRight, bottomRight, bottomLeft)
    }
}