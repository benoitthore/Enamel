package com.thorebenoit.enamel.kotlin.geometry.layout

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.geometry.alignement.*
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDataStore
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
        }

        class Fraction(val fraction: Float) : Division() {
            constructor(value: Number) : this(value.f)
        }

        object Slice : Division()
    }



    override fun serialize(dataStore: ELayoutDataStore) {
        val by = by

        val _a: Unit = when (by) {
            is EDivideLayout.Division.Slice -> {
                dataStore.add(0)
            }
            is EDivideLayout.Division.Distance -> {
                dataStore.add(1)
                dataStore.add(by.distance)

            }
            is EDivideLayout.Division.Fraction -> {
                dataStore.add(2)
                dataStore.add(by.fraction)

            }
        }
        dataStore.add(edge)
        dataStore.add(spacing)
        dataStore.add(snugged)
        dataStore.add(childLayouts)
    }

    override fun deserialize(dataStore: ELayoutDataStore) {
        edge = ERectEdge.values()[dataStore.readNumber().toInt()]
        spacing = dataStore.readNumber()
        snugged = dataStore.readBool()
        _childLayouts.clear()
        _childLayouts.addAll(dataStore.readLayouts())
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
