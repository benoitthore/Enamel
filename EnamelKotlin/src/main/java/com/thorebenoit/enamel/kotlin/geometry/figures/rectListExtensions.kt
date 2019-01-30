package com.thorebenoit.enamel.kotlin.geometry.figures

fun List<ERectImmutable>.union(buffer: ERect): ERect {
    if (isEmpty()) {
        return ERect()
    }

    var left = Float.MAX_VALUE
    var top = Float.MAX_VALUE
    var right = Float.MIN_VALUE
    var bottom = Float.MIN_VALUE
    forEach {
        if (it.left < left) {
            left = it.left
        }

        if (it.top < top) {
            top = it.top
        }

        if (it.right > right) {
            right = it.right
        }

        if (it.bottom > bottom) {
            bottom = it.bottom
        }
    }
    return ERectSides(
        top = top,
        left = left,
        right = right,
        bottom = bottom,
        buffer = buffer
    )
}