package com.thorebenoit.enamel.kotlin.geometry.primitives

import com.thorebenoit.enamel.kotlin.core.math.f

class EOffset(
    val top: Float,
    val right: Float,
    val bottom: Float,
    val left: Float
) : Tuple2 {

    override val v1: Number get() = horizontal
    override val v2: Number get() = vertical

    inline val horizontal get() = left + right
    inline val vertical get() = top + bottom

    companion object {
        val zero = EOffset()
    }

    constructor(
        all: Number
    ) : this(all, all, all, all)

    constructor(
        top: Number = 0f,
        right: Number = 0f,
        bottom: Number = 0f,
        left: Number = 0f
    ) : this(
        top.f,
        right.f,
        bottom.f,
        left.f
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        (other as? EOffset)?.let {
            if (top != other.top) return false
            if (right != other.right) return false
            if (bottom != other.bottom) return false
            if (left != other.left) return false
            return true
        }

        return false
    }

    override fun hashCode(): Int {
        var result = top.hashCode()
        result = 31 * result + right.hashCode()
        result = 31 * result + bottom.hashCode()
        result = 31 * result + left.hashCode()
        return result
    }


}