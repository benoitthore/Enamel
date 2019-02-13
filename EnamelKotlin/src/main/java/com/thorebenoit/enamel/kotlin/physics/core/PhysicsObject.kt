package com.thorebenoit.enamel.kotlin.physics.core

import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType

interface PhysicsObject {
    val velocity: EPoint
    val acceleration: EPoint
    val position: EPoint
    val maxVelocity: Float


    fun applyForce(force: EPointType) {
        acceleration.selfOffset(force)
    }

    fun update(deltaTime: Float) {
        velocity.selfOffset(acceleration.x * deltaTime, acceleration.y * deltaTime).selfLimitMagnitude(maxVelocity)
        position.selfOffset(velocity.x * deltaTime, velocity.y * deltaTime)

        // Clear acceleration after frame
        acceleration.set(0, 0)
    }
}