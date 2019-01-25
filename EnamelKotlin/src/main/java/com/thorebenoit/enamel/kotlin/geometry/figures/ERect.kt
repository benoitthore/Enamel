package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointImmutable
import com.thorebenoit.enamel.kotlin.geometry.primitives.set

open class ImmutableERect(
    open val origin: EPointImmutable = EPointImmutable(),
    open val size: ESizeImmutable = ESizeImmutable()
) {
    fun toMutable() = ERect(origin.toMutable(), size.toMutable())
}

class ERect(override var origin: EPoint = EPoint(), override var size: ESize = ESize()) :
    ImmutableERect(origin, size)


fun ImmutableERect.center(buffer: EPoint = EPoint()): EPoint {
    buffer.set(
        origin.x + (size.width / 2),
        origin.y + (size.height / 2)
    )
    return buffer
}

fun ImmutableERect.innerCircle(buffer: ECircle = ECircle()): ECircle {
    center(buffer.center)
    buffer.radius = size.min / 2f
    return buffer
}


fun ImmutableERect.outterCircle(buffer: ECircle = ECircle()): ECircle {
    center(buffer.center)
    buffer.radius = size.max
    return buffer
}

fun ESizeImmutable.toRect() = ImmutableERect(size = this)