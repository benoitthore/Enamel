package com.benoitthore.enamel.geometry.primitives.size

import com.benoitthore.enamel.geometry.builders.E

internal class ESizeMutableImpl internal constructor(width: Number, height: Number) :
    ESizeMutable {
    override var width: Float = width.toFloat()
    override var height: Float = height.toFloat()

    override fun toMutable(): ESizeMutable = E.SizeMutable()

    override fun toImmutable(): ESize = toMutable()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is ESizeMutable) return false

        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width.hashCode()
        result = 31 * result + height.hashCode()
        return result
    }

    override fun toString(): String {
        return "ESize(width=$width, height=$height)"
    }


}