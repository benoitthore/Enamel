package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.randomColor
import com.thorebenoit.enamel.kotlin.core.time.EDeltaTimer
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeImmutable
import com.thorebenoit.enamel.kotlin.geometry.primitives.*
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet


object ProcessingTestMain {
    @JvmStatic
    fun main(args: Array<String>) {

        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false

        val applet = KotlinPApplet.createApplet<MainApplet>()
        MainAppletPresenter(applet)
    }

}

interface PhysicsObject {
    val velocity: EPoint
    val acceleration: EPoint
    val position: EPoint


    fun applyForce(force: EPointType) {
        acceleration.selfOffset(force)
    }

    fun update(deltaTime: Float) {
        val deltaTime = 1
        velocity.selfOffset(acceleration.x * deltaTime, acceleration.y * deltaTime)
        position.selfOffset(velocity.x * deltaTime, velocity.y * deltaTime)

        // Clear acceleration after frame
        acceleration.set(0, 0)
    }
}

interface Steerer : PhysicsObject {
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


    fun getSteerAwayFrom(target: EPointType) = getSteerTowards(target).selfInverse()
}


data class Dot(
    override val position: EPoint,
    val angleType: EAngle = EAngle(),
    val color: Int = randomColor(),
    val radius: Int = 5
) :
    Steerer // TODO Refactor so it can use composition and steer towards/away from targets faster/slower
{

    override val maxSpeed: Float = 2f
    override val maxForce: Float = 0.01f
    override val velocity: EPoint = EPoint()
    override val acceleration: EPoint = EPoint()

}

interface DotDrawer {
    fun update()
    var dotList: List<Dot>
    val size: ESizeImmutable
    // x , y , isClicked
    val mousePosition: EPointType
    var onMouseClicked: () -> Unit
}

class MainAppletPresenter(val view: DotDrawer) {

    private val _dotList = mutableListOf<Dot>()

    init {
        view.onMouseClicked = {

            _dotList += Dot(view.mousePosition.toMutable())
            view.dotList = _dotList

        }

        startLoop()
    }

    private fun startLoop() {

        coroutine {
            while (true) {
                val timer = EDeltaTimer()
                timer.frame { deltaTime ->
                    // <frame>


                    _dotList.forEach { first ->
                        first.applyForce(first.getSteerTowards(view.mousePosition))

                        _dotList.forEach { second ->
                            if (first != second) {
                                if (first.position.distanceTo(second.position) < (second.radius + first.radius) * 5) {
                                    first.applyForce(first.getSteerAwayFrom(second.position))
                                }
                            }
                        }

                    }

                    // </frame>

                    _dotList.forEach { it.update(deltaTime) }

                    Thread.sleep((timer.targetFrameTime * deltaTime).toLong())

                    view.dotList = _dotList
                    view.update()
                }


            }
        }
    }

}


class MainApplet : KotlinPApplet(), DotDrawer {
    override fun update() {
        loop()
    }

    override var onMouseClicked: () -> Unit = {}

    override var dotList: List<Dot> = emptyList()
    override val size: ESizeImmutable get() = esize
    private val clickDistance = 200
    private var lastMouseDown: EPoint? = null

    init {


        onMousePressed {
            if (lastMouseDown == null) {
                lastMouseDown = mousePosition.copy()
            }
        }

        onMouseReleased {
            val distance = lastMouseDown?.distanceTo(mousePosition)
            lastMouseDown = null
            if (distance != null && distance < clickDistance) {
                onMouseClicked()
            }
        }
//        onMouseClicked { onMouseClicked() }
    }

    override fun draw() {
        background(255)

        stroke(0)
        strokeWeight(1f)

        dotList.forEach {
            fill(it.color)
            it.position.draw(it.radius)
        }

        noLoop()
        // DEBUG Draw mouse click radius
//        noFill()
//        stroke(0)
//        strokeWeight(0.5f)
//        lastMouseDown?.draw(clickDistance)
    }
}

