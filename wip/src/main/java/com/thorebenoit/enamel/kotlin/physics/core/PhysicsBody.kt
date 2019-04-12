package com.thorebenoit.enamel.kotlin.physics.core

import com.thorebenoit.enamel.geometry.primitives.EPointMutable
import com.thorebenoit.enamel.geometry.primitives.EPoint

class PhysicsBody(val maxVelocity: Float, val position: EPointMutable = EPointMutable()) {
    val velocity: EPointMutable = EPointMutable()
    val acceleration: EPointMutable = EPointMutable()


    fun addForce(force: EPoint) {
        acceleration.selfOffset(force)
    }

    // TODO Remove ?
    private fun addImpulse(force: EPoint) {
        velocity.selfOffset(force).selfLimitMagnitude(maxVelocity)
    }

    fun update(deltaTime: Float = 1f) {
        velocity.selfOffset(acceleration.x * deltaTime, acceleration.y * deltaTime).selfLimitMagnitude(maxVelocity)
        position.selfOffset(velocity.x * deltaTime, velocity.y * deltaTime)

        // TODO Use delta time instead ?
        // Clear acceleration after constraintFrame
        acceleration.set(0, 0)
    }
}