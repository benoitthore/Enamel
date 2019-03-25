package com.thorebenoit.enamel.kotlin.geometry.layout

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.geometry.alignement.*
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.toRect

class EDivideLayout(
    slice: ELayout = ELayoutLeaf(),
    remainder: ELayout = ELayoutLeaf(),
    var by: Division = Division.Slice,
    var edge: ERectEdge = ERectEdge.top,
    var spacing: Number = 0,
    var snugged: Boolean = true
) : ELayout {
    var slice: ELayout = slice
        set(value) {
            field = value
            childLayouts.clear()
            childLayouts.add(slice)
            childLayouts.add(remainder)
        }
    var remainder: ELayout = remainder
        set(value) {
            field = value
            childLayouts.clear()
            childLayouts.add(slice)
            childLayouts.add(remainder)
        }

    override val childLayouts: MutableList<ELayout> = mutableListOf(slice, remainder)

    override fun size(toFit: ESizeType): ESizeType {
        if (snugged) {
            return toFit
        }
        val (dividedSlice, dividedRemainder) = divide(toFit.toRect())
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
        class Distance(val distance: Float) : Division() {
            constructor(value: Number) : this(value.f)
        }

        class Fraction(val fraction: Float) : Division() {
            constructor(value: Number) : this(value.f)
        }

        object Slice : Division()
    }
}

// <Helpers>
private fun EDivideLayout.distance(toFit: ESizeType): Float = by.let { by ->
    when (by) {
        is EDivideLayout.Division.Distance -> by.distance
        is EDivideLayout.Division.Fraction -> by.fraction * toFit.along(edge.layoutAxis)
        is EDivideLayout.Division.Slice -> slice.size(toFit).along(edge.layoutAxis)
    }
}

private fun EDivideLayout.divide(frame: ERectType): Pair<ERectType, ERectType> {
    val distance = distance(frame.size)
    val divided = frame.divided(distance, edge)

    return divided.first to divided.second.divided(spacing, edge).second
}

// </Helpers>
