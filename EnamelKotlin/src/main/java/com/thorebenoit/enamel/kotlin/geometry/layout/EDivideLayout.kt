package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.geometry.alignement.*
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer

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
            _childLayouts.clear()
            _childLayouts.add(slice)
            _childLayouts.add(remainder)
        }
    var remainder: ELayout = remainder
        set(value) {
            field = value
            _childLayouts.clear()
            _childLayouts.add(slice)
            _childLayouts.add(remainder)
        }

    private val _childLayouts: MutableList<ELayout> = mutableListOf(slice, remainder)
    override val childLayouts: List<ELayout> get() = _childLayouts

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

            override fun toString(): String {
                return "Distance(distance=$distance)"
            }

        }

        class Fraction(val fraction: Float) : Division() {
            constructor(value: Number) : this(value.f)

            override fun toString(): String {
                return "Fraction(fraction=$fraction)"
            }

        }

        object Slice : Division()
    }


    override fun toString(): String {
        return "EDivideLayout(by=$by, edge=$edge, spacing=$spacing, snugged=$snugged, slice=$slice, remainder=$remainder)"
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
