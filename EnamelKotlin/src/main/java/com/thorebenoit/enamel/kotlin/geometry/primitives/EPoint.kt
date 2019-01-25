package com.thorebenoit.enamel.kotlin.geometry.primitives

import com.thorebenoit.enamel.kotlin.*
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeImmutable

open class EPointImmutable(open val x: Float = 0f, open val y: Float = 0f) {
    companion object {
        val zero = EPointImmutable(0f, 0f)
        val half = EPointImmutable(0.5f,0.5f)
        val unit = EPointImmutable(1f, 1f)
    }

    constructor(x: Number, y: Number) : this(x.f, y.f)

    fun toMutable() = EPoint(x,y)

    override fun toString(): String {
        return "EPointImmutable($x ; $y)"
    }
}

class EPoint(override var x: Float = 0f, override var y: Float = 0f) : EPointImmutable(x, y) {
    companion object {
        val zero get() = EPoint(0f, 0f)
        val half get() = EPoint(0.5f,0.5f)
        val unit get() = EPoint(1f, 1f)
    }

    constructor(x: Number, y: Number) : this(x.f, y.f)

    override fun toString(): String {
        return "EPoint($x ; $y)"
    }
}

/////////////////////////
/////////////////////////
/////////////////////////
fun EPoint.copy() =
    EPoint(x, y)

fun EPoint.toImmutable() =
    EPointImmutable(x, y)

// TODO Make this public only after refactoring is done
//operator fun EPoint.plus(other: EPoint) = EPoint(x + other.x, y + other.y)

fun EPoint.set(x: Number, y: Number) = apply { this.x = x.f; this.y = y.f }
fun EPoint.set(other: EPointImmutable) = set(other.x, other.y)

fun EPoint.set(angle: EAngle, magnitude: Number) = set(angle.cos * magnitude.f, angle.sin * magnitude.f)

fun EPoint.offset(x: Number, y: Number) = set(this.x + x.f, this.y + y.f)
fun EPoint.offset(n: Number) = offset(n, n)
fun EPoint.offset(other: EPoint) = offset(other.x, other.y)

fun EPoint.offsetTowards(distance: Number, towards: EPoint): EPoint {
    val fromX = x
    val fromY = y
    set(angle = angleTo(towards), magnitude = distance)
    return set(x + fromX, y + fromY)
}

fun EPoint.offsetFrom(distance: Number, from: EPoint) = from.offsetTowards(distance, this)
fun EPoint.offsetAngle(angle: EAngle, distance: Number): EPoint {
    val fromX = x
    val fromY = y
    set(angle, distance)
    return set(x + fromX, y + fromY)
}

fun EPoint.scale(x: Number, y: Number) = set(this.x * x.f, this.y * y.f)
fun EPoint.scale(n: Number) = scale(n, n)
fun EPoint.scale(other: EPoint) = scale(x, y)
fun EPoint.angleTo(point: EPoint): EAngle =
    Math.atan2(
        ((point.y - y).d), ((point.x - x).d)
    ).radians()

fun EPoint.magnitude(): Number = Math.hypot(x.d, y.d)
fun EPoint.distanceTo(o: EPoint) = this.distanceTo(o.x, o.y)
fun EPoint.distanceTo(x2: Number, y2: Number) = Math.hypot((x2.d - x), (y2.d - y)).f
fun EPoint.rotateBy(angle: EAngle, magnitude: Number): EPoint {
    val magnitude = magnitude.f
    return set(x + angle.cos * magnitude, y + angle.sin * magnitude)
}

fun EPoint.rotateAround(angle: EAngle, center: EPoint): EPoint {
    val angleTo = center.angleTo(this)
    val distance = center.distanceTo(this)
    val totalAngle = angle + angleTo
    val x = center.x + totalAngle.cos * distance
    val y = center.y + totalAngle.sin * distance
    return set(x, y)
}

fun EPoint.normaliseIn(frame: ESizeImmutable): EPoint = x / frame.width point y / frame.height
infix fun Number.point(other: Number): EPoint =
    EPoint(this, other)
