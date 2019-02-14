package com.thorebenoit.enamel.kotlin.physics.steering

import com.thorebenoit.enamel.kotlin.geometry.GeometryBufferProvider
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.primitives.EAngleType
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.primitives.minus
import com.thorebenoit.enamel.kotlin.physics.core.PhysicsBody


/**
 * This class is used to make a decision on steering, it's not a physics object
 */
data class SteeringController(
    var torque: Float,
    val physicsBody: PhysicsBody
) {

    inline fun withTorqueMult(torqueOverride: Float, crossinline block: SteeringController.() -> Unit)  = withTorque(torqueOverride,block)
    inline fun withTorque(torqueOverride: Float, block: SteeringController.() -> Unit) {
        synchronized(this) {
            val defaultTorque = torque
            torque = torqueOverride
            block()
            torque = defaultTorque
        }
    }

    fun steerTowards(target: EPointType) = physicsBody.addForce(getSteerTowards(target))
    fun steerAway(target: EPointType) = physicsBody.addForce(getSteerAway(target))
    fun steerAngle(angle: EAngleType) = physicsBody.addForce(getSteerAngle(angle))
    fun steerBackwards() = physicsBody.addForce(getSteerBackwards())
    fun steerInside(rect: ERectType) = physicsBody.addForce(
        getSteerTowards(
            rect.center(GeometryBufferProvider.point())
        )
    )

    fun getSteerAngle(angle: EAngleType): EPoint {
        val desired = EPoint().selfOffsetAngle(angle, 1).selfMult(physicsBody.maxVelocity)

        val steer = desired - physicsBody.velocity
        steer.selfLimitMagnitude(torque)

        return steer
    }

    fun getSteerBackwards(): EPointType = getSteerAngle(-(physicsBody.velocity.heading()))

    fun getSteerTowards(target: EPointType): EPoint {

        return getSteerAngle(physicsBody.position.angleTo(target))
        val desired = target - physicsBody.position
        desired.selfNormalize().selfMult(physicsBody.maxVelocity)

        val steer = desired - physicsBody.velocity
        steer.selfLimitMagnitude(torque)

        return steer
    }


    fun getSteerAway(target: EPointType): EPoint {
        // TODO This doesn't work as it should
        val desired = target - physicsBody.position
        desired.selfNormalize().selfInverse().selfMult(physicsBody.maxVelocity)

        val steer = desired - physicsBody.velocity
        steer.selfLimitMagnitude(torque)

        return steer
    }
}

