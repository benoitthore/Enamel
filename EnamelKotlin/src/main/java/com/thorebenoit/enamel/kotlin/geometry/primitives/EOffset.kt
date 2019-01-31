package com.thorebenoit.enamel.kotlin.geometry.primitives

class EOffset(
    val top: Float = 0f,
    val right: Float = 0f,
    val bottom: Float = 0f,
    val left: Float = 0f
) {
    companion object {
        val zero = EOffset()
    }
}