package com.thorebenoit.enamel.geometry.layout.dsl

import com.thorebenoit.enamel.geometry.alignement.EAlignment
import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.geometry.figures.size
import com.thorebenoit.enamel.geometry.layout.EJustifiedLayout
import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.geometry.layout.ESizingLayout
import com.thorebenoit.enamel.geometry.primitives.Tuple2


fun List<ELayout>.justified(alignment: EAlignment) = EJustifiedLayout(this.toMutableList(), alignment)
/*
    class Size(val size: ESize) : ELayoutSpace()
    class Width(val width: Number) : ELayoutSpace()
    class Height(val height: Number) : ELayoutSpace()
    class Scale(val horizontal: Number?, val vertical: Number?) : ELayoutSpace()
    class AspectFitting(val size: ESize) : ELayoutSpace()
    class AspectFilling(val size: ESize) : ELayoutSpace()
    class Func(val f: (ESize) -> ESize) : ELayoutSpace()
 */

fun ELayout.sized(size: ESize) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Size(size))
fun ELayout.sized(width: Number, height: Number) = sized(width size height)
fun ELayout.sizedSquare(size: Number) = sized(size size size)

fun ELayout.width(width: Number) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Width(width))
fun ELayout.height(height: Number) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Height(height))

fun ELayout.fitting(size: ESize) = ESizingLayout(this, ESizingLayout.ELayoutSpace.AspectFitting(size))
fun ELayout.fitting(width: Number, height: Number) = fitting(width size height)

fun ELayout.filling(size: ESize) = ESizingLayout(this, ESizingLayout.ELayoutSpace.AspectFilling(size))
fun ELayout.filling(width: Number, height: Number) = filling(width size height)

fun ELayout.scaled(x: Number? = null, y: Number? = null) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Scale(x, y))
fun ELayout.scaled(tuple2: Tuple2) = scaled(tuple2.v1, tuple2.v2)

//////

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

