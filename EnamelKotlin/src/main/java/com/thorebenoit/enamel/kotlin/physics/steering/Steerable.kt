package com.thorebenoit.enamel.kotlin.physics.steering

import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.physics.core.PhysicsObject


// TODO Refactor so it can use composition and steer towards/away from targets faster/slower
/*
    Steerable is a class that takes a Physics object
    SteeringBegaviour  is a class takes a Steerable
 */
interface Steerable : PhysicsObject {
    val maxSpeed: Float
    val maxForce: Float

    fun getSteerTowards(target: EPointType): EPoint {
        val desired = target - position
        desired.selfNormalize()

        desired.mult(maxSpeed)
        val steer = desired - velocity
        steer.selfLimitMagnitude(maxForce)

        return steer
    }


    fun getSteerAwayFrom(target: EPointType): EPoint {
        // TODO This doesn't work as it should
        val desired = target - position
        desired.selfNormalize()

        desired.mult(maxSpeed)
        val steer = desired - velocity
        steer.selfLimitMagnitude(maxForce)

        return steer.selfInverse()
    }
}
