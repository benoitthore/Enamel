package com.thorebenoit.enamel.geometry.layout

import com.thorebenoit.enamel.geometry.alignement.*
import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.geometry.figures.divided
import com.thorebenoit.enamel.geometry.figures.union
import com.thorebenoit.enamel.core.math.f
import com.thorebenoit.enamel.geometry.toRect


class EDivideLayout(
    slice: ELayout,
    remainder: ELayout,
    var by: Division,
    var edge: ERectEdge,
    var spacing: Number,
    var snugged: Boolean
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

    // TODO This should wrap_content
    /*
    ex where it's not working:
        val leftLayout = "A".layoutTag
        val centerLayout = listOf("B", "BB")
            .layoutTag
            .map { it.width(100.dp) }
            .stacked(bottomLeft)
            .leaf

        val rightLayout = "CCC".layoutTag

        leftLayout
            .aligned(left, centerLayout)
            .arranged(topLeft)
            .sendToPlayground()
//
     */
    override fun size(toFit: ESize): ESize {
        if (!snugged) {
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

    override fun arrange(frame: ERect) {
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
private fun EDivideLayout.distance(toFit: ESize): Float = by.let { by ->
    when (by) {
        is EDivideLayout.Division.Distance -> by.distance
        is EDivideLayout.Division.Fraction -> by.fraction * toFit.along(edge.layoutAxis)
        is EDivideLayout.Division.Slice -> slice.size(toFit).along(edge.layoutAxis)
    }
}

private fun EDivideLayout.divide(frame: ERect): Pair<ERect, ERect> {
    val distance = distance(frame.size)
    val divided = frame.divided(distance, edge)

    return divided.first to divided.second.divided(spacing, edge).second
}

// </Helpers>
