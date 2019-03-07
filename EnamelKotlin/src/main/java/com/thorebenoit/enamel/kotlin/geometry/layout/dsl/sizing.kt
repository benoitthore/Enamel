package com.thorebenoit.enamel.kotlin.geometry.layout.dsl

import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.layout.EJustifiedLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ESizingLayout
import com.thorebenoit.enamel.kotlin.geometry.primitives.Tuple2


fun List<ELayout>.justified(alignment: EAlignment) = EJustifiedLayout(this, alignment)
/*
    class Size(val size: ESizeType) : ELayoutSpace()
    class Width(val width: Number) : ELayoutSpace()
    class Height(val height: Number) : ELayoutSpace()
    class Scale(val horizontal: Number?, val vertical: Number?) : ELayoutSpace()
    class AspectFitting(val size: ESizeType) : ELayoutSpace()
    class AspectFilling(val size: ESizeType) : ELayoutSpace()
    class Func(val f: (ESizeType) -> ESizeType) : ELayoutSpace()
 */

fun ELayout.sized(size: ESizeType) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Size(size))
fun ELayout.sized(width: Number, height: Number) = sized(width size height)
fun ELayout.sizedSquare(size: Number) = sized(size size size)

fun ELayout.width(width: Number) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Width(width))
fun ELayout.height(height: Number) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Width(height))

fun ELayout.fitting(size: ESizeType) = ESizingLayout(this, ESizingLayout.ELayoutSpace.AspectFitting(size))
fun ELayout.fitting(width: Number, height: Number) = fitting(width size height)

fun ELayout.filling(size: ESizeType) = ESizingLayout(this, ESizingLayout.ELayoutSpace.AspectFilling(size))
fun ELayout.filling(width: Number, height: Number) = filling(width size height)

fun ELayout.scaled(x: Number, y: Number) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Scale(x, y))
fun ELayout.scaled(tuple2: Tuple2) = scaled(tuple2.v1, tuple2.v2)

//////

fun List<ELayout>.sized(size: ESizeType) = map { it.sized(size) }
fun List<ELayout>.sized(width: Number, height: Number) = map { it.sized(width, height) }
fun List<ELayout>.sizedSquare(size: Number) = map { it.sizedSquare(size) }

fun List<ELayout>.width(width: Number) = map { it.width(width) }
fun List<ELayout>.height(height: Number) = map { it.height(height) }

fun List<ELayout>.fitting(size: ESizeType) = map { it.fitting(size) }
fun List<ELayout>.fitting(width: Number, height: Number) = map { it.fitting(width, height) }

fun List<ELayout>.filling(size: ESizeType) = map { it.filling(size) }
fun List<ELayout>.filling(width: Number, height: Number) = map { it.filling(width, height) }

fun List<ELayout>.scaled(tuple2: Tuple2) = map { it.scaled(tuple2) }
fun List<ELayout>.scaled(x: Number, y: Number) = map { it.scaled(x, y) }

