package com.benoitthore.enamel.android.bubble

import com.benoitthore.enamel.core.print
import com.benoitthore.enamel.core.threading.coroutine
import com.benoitthore.enamel.core.threading.singleThreadCoroutine
import com.benoitthore.enamel.geometry.figures.ECircleMutable
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.lerp
import com.benoitthore.enamel.geometry.primitives.EAngle
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.geometry.primitives.times
import com.benoitthore.enamel.layout.android.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private typealias touchListener = (EPoint) -> Unit

class BubbleController(val view: BubbleController.View) {
    interface View {
        var bubble: ECircleMutable
        fun update()
        val frame: ERect
        var handleTouch: (EPoint) -> Boolean
        var touchListener: (isDown: Boolean, current: EPoint?, previous: EPoint?) -> Unit
    }

    private val bodyCtrl = BodyController(PhysicsBody(maxVelocity = 1920 / 60f))

    fun start() {
        bodyCtrl.position = view.bubble.center

        view.apply {


            var follow: EPoint? = null
            touchListener = { isDown: Boolean, current: EPoint?, previous: EPoint? ->
                follow = current
            }

            singleThreadCoroutine {
                while (true) {

                    // CHOOSE TARGET
                    follow?.let {
                        if (view.bubble.contains(it)) {
                            bodyCtrl.stop()
                        } else {
                            bodyCtrl.follow(it)

                        }
//                    bodyCtrl.constraintTo(frame)
                        Unit
                    }
                    bodyCtrl.constraintTo(frame)
                    // UPDATE
                    view.bubble.set(center = bodyCtrl.position)
                    bodyCtrl.body.update()
                    view.update()

                    // FRAME DELAY
                    delay(16)
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

class BodyController(val body: PhysicsBody) {

    var position: EPoint
        get() = body.position
        set(value) {
            body.position.set(value)
        }
    val steeringBehaviour = SteeringBehaviour(body, 10)

    fun constraintTo(frame: ERect) {
        if (!frame.contains(body.position)) {
            // TODO
            steeringBehaviour.steerInside(frame)
        }
    }

    fun follow(target: EPoint) {
//        body.position.lerp(0.1,body.position,target)
        steeringBehaviour.steerTowards(target)
    }

    fun stop() {
        body.velocity.selfMult(0.75)
    }

}


class PhysicsBody(maxVelocity: Number, val position: EPointMutable = EPointMutable()) {
    val maxVelocity: Float = maxVelocity.toFloat()
    val velocity: EPointMutable = EPointMutable()
    val acceleration: EPointMutable = EPointMutable()

//    var friction: Number = 0.000000001f

    fun addForce(force: EPoint) {
        acceleration.selfOffset(force)
    }

    fun update(deltaTime: Float = 1f) {
        velocity.selfOffset(acceleration.x * deltaTime, acceleration.y * deltaTime)
            .selfLimitMagnitude(maxVelocity)
        position.selfOffset(velocity.x * deltaTime, velocity.y * deltaTime)


        // TODO Use delta time instead ?
        // Clear acceleration after constraintFrame

//        velocity.selfMult(friction)
        acceleration.set(0, 0)
    }
}

class SteeringBehaviour(
    var physicsBody: PhysicsBody,
    var torque: Number
) {


    fun steerTowards(target: EPoint) = physicsBody.addForce(getSteerTowards(target))
    fun steerAway(target: EPoint) = physicsBody.addForce(getSteerAway(target))
    fun steerAngle(angle: EAngle) = physicsBody.addForce(getSteerAngle(angle))
    fun steerBackwards() = physicsBody.addForce(getSteerBackwards())
    fun steerInside(rect: ERect) = physicsBody.addForce(
        getSteerTowards(rect.center())
    )

    fun getSteerAngle(angle: EAngle): EPointMutable {
        val desired = EPointMutable().selfOffsetAngle(angle, 1).selfMult(physicsBody.maxVelocity)

        val steer = desired.sub(physicsBody.velocity)
        steer.selfLimitMagnitude(torque)

        return steer
    }

    fun getSteerBackwards(): EPoint = getSteerAngle(-(physicsBody.velocity.heading()))

    fun getSteerTowards(target: EPoint): EPointMutable {
        return getSteerAngle(physicsBody.position.angleTo(target))
    }

    fun getSteerAway(target: EPoint): EPointMutable {
        // TODO This doesn't work as it should
        val desired = target.sub(physicsBody.position)
        desired.selfNormalize().selfInverse().selfMult(physicsBody.maxVelocity)

        val steer = desired.sub(physicsBody.velocity)
        steer.selfLimitMagnitude(torque)

        return steer
    }
}