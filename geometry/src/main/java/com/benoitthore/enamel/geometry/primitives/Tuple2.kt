package com.benoitthore.enamel.geometry.primitives

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.ESize

/**
 * This file is used to facilitate development but allocates a new Tuple every time a function is called
 */

interface Tuple2 {
    val v1: Number
    val v2: Number
}

inline operator fun EPoint.unaryMinus() = inverse()

inline operator fun EPoint.div(other: Tuple2) = dividedBy(other)
inline operator fun EPoint.div(n: Number) = dividedBy(n)

inline operator fun EPoint.times(other: Tuple2) = mult(other)
inline operator fun EPoint.times(n: Number) = mult(n)

inline operator fun EPoint.plus(other: Tuple2) = offset(other)
inline operator fun EPoint.plus(n: Number) = offset(n)

inline operator fun EPoint.minus(other: Tuple2) = sub(other)
inline operator fun EPoint.minus(n: Number) = sub(n)


inline operator fun Number.times(p: EPoint) = p.times(this)
inline operator fun Number.plus(p: EPoint) = p.offset(this)
inline operator fun Number.div(p: EPoint): EPoint {
    val n = toFloat()
    return p.copy(n / p.x, n / p.y)
}


inline operator fun ESize.unaryMinus() = E.msize(-width, -height)

inline operator fun ESize.div(other: Tuple2) = dividedBy(other)
inline operator fun ESize.div(n: Number) = dividedBy(n)

inline operator fun ESize.times(other: Tuple2) = scale(other)
inline operator fun ESize.times(n: Number) = scale(n)

inline operator fun ESize.plus(other: Tuple2) = expand(other)
inline operator fun ESize.plus(n: Number) = expand(n)

inline fun ESize.sub(other: Tuple2) = inset(other)
inline fun ESize.sub(n: Number) = inset(n)


inline operator fun Number.times(p: ESize) = p.times(this)
inline operator fun Number.plus(p: ESize) = p.expand(this)

