package com.benoitthore.enamel.geometry.primitives.size

internal class ESizeMutableImpl internal constructor(width: Number, height: Number) :
    ESizeMutable {
    override var width: Float = width.toFloat()
    override var height: Float = height.toFloat()
}