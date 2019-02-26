package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.geometry.alignement.*
import com.thorebenoit.enamel.kotlin.geometry.figures.*

class EDivideLayout(
    val slice: ELayout,
    val remainder: ELayout,
    val by: Division,
    val edge: ERectEdge,
    val spacing: Number = 0,
    val snugged: Boolean = true
) : ELayout {


    override val childLayouts: List<ELayout> = listOf(slice, remainder)

    override fun size(toFit: ESizeType): ESizeType {
        if (snugged) {
            return toFit
        }
        val (dividedSlice, dividedRemainder) = divide(toFit.rect())
        val axis = edge.layoutAxis.opposite

        val snugExtent = listOf(
            slice.size(dividedSlice.size),
            remainder.size(dividedRemainder.size)
        ).union().along(axis)

        return toFit.with(axis, snugExtent)
    }

    override fun arrange(frame: ERectType) {
        val (dividedSlice, dividedRemainder) = divide(frame)
        slice.arrange(dividedSlice)
        remainder.arrange(dividedRemainder)
    }


    sealed class Division {
        abstract val value: Float

        class Distance(override val value: Float) : Division() {
            constructor(value: Number) : this(value.f)
        }

        class Fraction(override val value: Float) : Division() {
            constructor(value: Number) : this(value.f)
        }

        class Slice : Division() {
            override val value: Float = 0f
        }
    }
}

// <Helpers>
private fun EDivideLayout.distance(toFit: ESizeType): Float = when (by) {
    is EDivideLayout.Division.Distance -> by.value
    is EDivideLayout.Division.Fraction -> by.value * toFit.along(edge.layoutAxis)
    is EDivideLayout.Division.Slice -> slice.size(toFit).along(edge.layoutAxis)
}

private fun EDivideLayout.divide(frame: ERectType): Pair<ERectType, ERectType> {
    val distance = distance(frame.size)
    val divided = frame.divided(distance, edge)

    return divided.first to divided.second.divided(spacing, edge).second
}

// </Helpers>
