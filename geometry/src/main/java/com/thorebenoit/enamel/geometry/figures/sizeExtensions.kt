package com.thorebenoit.enamel.geometry.figures

import com.thorebenoit.enamel.core.math.random


fun List<ESizeType>.union(buffer: ESize = ESize()): ESize {
    var width = Float.MIN_VALUE
    var height = Float.MIN_VALUE

    forEach {
        if (it.width > width) {
            width = it.width
        }
        if (it.height > height) {
            height = it.height
        }
    }

    return width size height
}

fun randomSize(maxWidth: Number = 1, maxHeight: Number = 1) = random(maxWidth) size random(maxHeight)