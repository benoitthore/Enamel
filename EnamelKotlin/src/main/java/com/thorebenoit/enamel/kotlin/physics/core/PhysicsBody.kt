package com.thorebenoit.enamel.kotlin.physics.core

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType

class PhysicsBody(val maxVelocity: Float, val position: EPoint = EPoint()) {
    val velocity: EPoint = EPoint()
    val acceleration: EPoint = EPoint()


    fun addForce(force: EPointType) {
        acceleration.selfOffset(force)
    }

    fun addImpulse(force: EPointType){
        velocity.selfOffset(force).selfLimitMagnitude(maxVelocity)
    }

    fun update(deltaTime: Float = 1f) {
        velocity.selfOffset(acceleration.x * deltaTime, acceleration.y * deltaTime).selfLimitMagnitude(maxVelocity)
        position.selfOffset(velocity.x * deltaTime, velocity.y * deltaTime)

        // Clear acceleration after constraintFrame
        acceleration.set(0, 0)
    }
}