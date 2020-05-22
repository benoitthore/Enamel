package com.benoitthore.enamel.geometry.primitives.offset

import com.benoitthore.enamel.geometry.allocateDebugMessage

internal class EOffsetMutableImpl(
    override var top: Float,
    override var right: Float,
    override var bottom: Float,
    override var left: Float
) : EOffsetMutable {
    init {
        allocateDebugMessage()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EOffset) return false

        if (top != other.top) return false
        if (right != other.right) return false
        if (bottom != other.bottom) return false
        if (left != other.left) return false

        return true
    }

    override fun hashCode(): Int {
        var result = top.hashCode()
        result = 31 * result + right.hashCode()
        result = 31 * result + bottom.hashCode()
        result = 31 * result + left.hashCode()
        return result
    }

    override fun toString(): String {
        return "EOffset(top=$top, right=$right, bottom=$bottom, left=$left)"
    }

}