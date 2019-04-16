package com.thorebenoit.enamel.geometry.primitives

import com.thorebenoit.enamel.geometry.figures.ESizeMutable
import com.thorebenoit.enamel.geometry.figures.ESize

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


inline operator fun ESize.unaryMinus() = ESizeMutable(-width, -height)

inline operator fun ESize.div(other: Tuple2) = dividedBy(other)
inline operator fun ESize.div(n: Number) = dividedBy(n)

inline operator fun ESize.times(other: Tuple2) = scale(other)
inline operator fun ESize.times(n: Number) = scale(n)

inline operator fun ESize.plus(other: Tuple2) = expand(other)
inline operator fun ESize.plus(n: Number) = expand(n)

inline operator fun ESize.minus(other: Tuple2) = inset(other)
inline operator fun ESize.minus(n: Number) = inset(n)


inline operator fun Number.times(p: ESize) = p.times(this)
inline operator fun Number.plus(p: ESize) = p.expand(this)

