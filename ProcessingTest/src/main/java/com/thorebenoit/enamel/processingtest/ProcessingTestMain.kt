package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.randomColor
import com.thorebenoit.enamel.kotlin.core.time.EDeltaTimer
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeImmutable
import com.thorebenoit.enamel.kotlin.geometry.primitives.*
import com.thorebenoit.enamel.kotlin.physics.physicsLoop
import com.thorebenoit.enamel.kotlin.physics.steering.Steerable
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import java.util.*


object ProcessingTestMain {


    @JvmStatic
    fun main(args: Array<String>) {

        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false

        val applet = KotlinPApplet.createApplet<MainApplet>()
        MainAppletPresenter(applet)
    }

}


data class Dot(
    override val position: EPoint,
    val angleType: EAngle = EAngle(),
    val color: Int = randomColor(),
    val radius: Int = 5
) :
    Steerable {
    override val maxVelocity: Float = 0.8f

    override val maxSpeed: Float = 1f
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

    private val _dotList = Collections.synchronizedCollection(mutableListOf<Dot>())

    init {
        view.onMouseClicked = {

            _dotList += Dot(view.mousePosition.toMutable())
            view.dotList = _dotList.toList()

        }

        startLoop()
    }

    private fun startLoop() {
        physicsLoop { deltaTime ->

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


            view.dotList = _dotList.toList()
            view.update()
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

