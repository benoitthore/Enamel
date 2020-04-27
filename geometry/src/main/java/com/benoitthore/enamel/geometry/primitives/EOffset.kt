package com.benoitthore.enamel.geometry.primitives

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.allocateDebugMessage

interface EOffset : Tuple2 {


    val top: Float
    val right: Float
    val bottom: Float
    val left: Float

    override val v1: Number get() = horizontal
    override val v2: Number get() = vertical

    val horizontal get() = left + right
    val vertical get() = top + bottom
}

interface EOffsetMutable : EOffset, Resetable {
    override var top: Float
    override var right: Float
    override var bottom: Float
    override var left: Float

    override fun reset() = set(0, 0, 0, 0)

    fun set(
        top: Number = this.top,
        right: Number = this.right,
        bottom: Number = this.bottom,
        left: Number = this.left
    ) {
        this.top = top.toFloat()
        this.right = right.toFloat()
        this.bottom = bottom.toFloat()
        this.left = left.toFloat()
    }

    class Impl(
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
            if (javaClass != other?.javaClass) return false

            other as Impl

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
}