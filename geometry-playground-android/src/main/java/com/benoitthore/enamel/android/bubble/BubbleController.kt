package com.benoitthore.enamel.android.bubble

import com.benoitthore.enamel.geometry.figures.ECircleMutable
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.lerp
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.layout.android.dp


private typealias touchListener = (EPoint) -> Unit

class BubbleController(val view: BubbleController.View) {
    interface View {
        var bubble: ECircleMutable
        fun update()
        val frame: ERect
        var handleTouch: (EPoint) -> Boolean
        var touchListener: (isDown: Boolean, current: EPoint?, previous: EPoint?) -> Unit
    }

    private val bodyCtrl = BodyController(PhysicsBody(maxVelocity = 10.dp))

    fun start() {
        view.apply {
            touchListener = { isDown: Boolean, current: EPoint?, previous: EPoint? ->
                current?.let { current ->

                    // TODO Add physics instead of interpolating
//                    bubble.center.lerp(0.125, bubble.center, current)

                    bodyCtrl.follow(current)
                    bodyCtrl.constraintTo(frame)

                    view.bubble.set(center = bodyCtrl.position)
                    update()
                }
            }

            handleTouch = {
                true
                // TODO Remove
//                view.bubble.contains(it)
            }
        }
    }

}

class BodyController(private val body: PhysicsBody) {

    val position: EPoint get() = body.position

    fun constraintTo(frame: ERect) {
//        if (!frame.contains(body.position)) {
//            // TODO
//            body.position.set(frame.center())
//        }
    }

    fun follow(target: EPoint) {
        body.position.set(target)
    }

}


class PhysicsBody(maxVelocity: Number, val position: EPointMutable = EPointMutable()) {
    val maxVelocity: Float = maxVelocity.toFloat()
    val velocity: EPointMutable = EPointMutable()
    val acceleration: EPointMutable = EPointMutable()


    fun addForce(force: EPoint) {
        acceleration.selfOffset(force)
    }

    fun update(deltaTime: Float = 1f) {
        velocity.selfOffset(acceleration.x * deltaTime, acceleration.y * deltaTime)
            .selfLimitMagnitude(maxVelocity)
        position.selfOffset(velocity.x * deltaTime, velocity.y * deltaTime)

        // TODO Use delta time instead ?
        // Clear acceleration after constraintFrame
        acceleration.set(0, 0)
    }
}