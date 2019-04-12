package com.thorebenoit.enamel.geometry.primitives

import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.geometry.figures.ESizeType

/**
 * This file is used to facilitate development of HIGHER LEVEL APIs, it doesn't care about allocations
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

inline operator fun EPoint.minus(other: Tuple2) = minus(other)
inline operator fun EPoint.minus(n: Number) = minus(n)


inline operator fun Number.times(p: EPoint) = p.times(this)
inline operator fun Number.plus(p: EPoint) = p.offset(this)


inline operator fun ESizeType.unaryMinus() = ESize(-width, -height)

inline operator fun ESizeType.div(other: Tuple2) = dividedBy(other)
inline operator fun ESizeType.div(n: Number) = dividedBy(n)

inline operator fun ESizeType.times(other: Tuple2) = scale(other)
inline operator fun ESizeType.times(n: Number) = scale(n)

inline operator fun ESizeType.plus(other: Tuple2) = expand(other)
inline operator fun ESizeType.plus(n: Number) = expand(n)

inline operator fun ESizeType.minus(other: Tuple2) = inset(other)
inline operator fun ESizeType.minus(n: Number) = inset(n)


inline operator fun Number.times(p: ESizeType) = p.times(this)
inline operator fun Number.plus(p: ESizeType) = p.expand(this)

