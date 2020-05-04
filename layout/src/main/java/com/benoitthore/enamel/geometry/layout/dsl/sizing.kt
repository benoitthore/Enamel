package com.benoitthore.enamel.geometry.layout.dsl

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.size
import com.benoitthore.enamel.geometry.layout.EJustifiedLayout
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.ESizingLayout
import com.benoitthore.enamel.geometry.layout.EWeightLayout
import com.benoitthore.enamel.geometry.primitives.Tuple2


fun List<ELayout>.justified(alignment: EAlignment) =
    EJustifiedLayout(this.toMutableList(), alignment)

fun ELayout.sized(size: ESize) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Size(size))
fun ELayout.sized(width: Number, height: Number) = sized(width size height)
fun ELayout.sizedSquare(size: Number) = sized(size size size)

fun ELayout.width(width: Number) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Width(width))
fun ELayout.height(height: Number) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Height(height))

fun ELayout.fitting(size: ESize) =
    ESizingLayout(this, ESizingLayout.ELayoutSpace.AspectFitting(size))

fun ELayout.fitting(width: Number, height: Number) = fitting(width size height)

fun ELayout.filling(size: ESize) =
    ESizingLayout(this, ESizingLayout.ELayoutSpace.AspectFilling(size))

fun ELayout.filling(width: Number, height: Number) = filling(width size height)

fun ELayout.scaled(x: Number? = null, y: Number? = null) =
    ESizingLayout(this, ESizingLayout.ELayoutSpace.Scale(x, y))

fun ELayout.scaled(tuple2: Tuple2) = scaled(tuple2.v1, tuple2.v2)

// Lists

fun List<ELayout>.sized(size: ESize) = map { it.sized(size) }
fun List<ELayout>.sized(width: Number, height: Number) = map { it.sized(width, height) }
fun List<ELayout>.sizedSquare(size: Number) = map { it.sizedSquare(size) }

fun List<ELayout>.width(width: Number) = map { it.width(width) }
fun List<ELayout>.height(height: Number) = map { it.height(height) }

fun List<ELayout>.fitting(size: ESize) = map { it.fitting(size) }
fun List<ELayout>.fitting(width: Number, height: Number) = map { it.fitting(width, height) }

fun List<ELayout>.filling(size: ESize) = map { it.filling(size) }
fun List<ELayout>.filling(width: Number, height: Number) = map { it.filling(width, height) }

fun List<ELayout>.scaled(tuple2: Tuple2) = map { it.scaled(tuple2) }
fun List<ELayout>.scaled(x: Number, y: Number) = map { it.scaled(x, y) }

// Weights
fun List<ELayout>.equallySized(alignment: EAlignment, spacing: Number = 0) =
    EWeightLayout(
        alignment = alignment,
        children = this,
        weights = List(size) { 1 },
        spacing = spacing
    )

fun List<Pair<ELayout, Number>>.weighted(
    alignment: EAlignment,
    spacing: Number = 0
): EWeightLayout {

    val childLayouts = ArrayList<ELayout>(size)
    val weights = ArrayList<Number>(size)

    forEach { (layout, weight) ->
        childLayouts += layout
        weights += weight
    }

    return EWeightLayout(
        alignment = alignment,
        children = childLayouts,
        weights = weights,
        spacing = spacing
    )
}
