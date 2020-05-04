package com.benoitthore.enamel.geometry.primitives.point

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

internal fun EPoint._angleTo(x: Number, y: Number): Double =
    atan2(
        ((y.f - this.y).d), ((x.f - this.x).d)
    )

internal fun EPoint._offsetAngle(
    angleRadians: Number,
    distance: Number,
    target: EPointMutable = E.PointMutable()
): EPointMutable {
    val fromX = x
    val fromY = y
    target._set(angleRadians, distance)
    return target.set(target.x + fromX, target.y + fromY)
}

// Used to set angle without having to allocate one
internal fun EPointMutable._set(angle: Number, magnitude: Number) =
    set(cos(angle.toFloat()) * magnitude.f, sin(angle.toFloat()) * magnitude.f)


/////////////////////////
/////////////////////////
/////////////////////////

/////////////////////////
/////////////////////////
/////////////////////////

infix fun Number.point(other: Number): EPointMutable =
    E.PointMutable(this, other)


val List<EPoint>.length: Float
    get() {
        var last: EPoint? = null
        var total = 0f
        forEach { p ->
            last?.let { last ->
                total += last.distanceTo(p)
            }
            last = p
        }
        return total
    }