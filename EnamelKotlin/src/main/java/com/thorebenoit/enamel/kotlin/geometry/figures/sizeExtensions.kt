package com.thorebenoit.enamel.kotlin.geometry.figures


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