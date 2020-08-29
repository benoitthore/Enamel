package com.benoitthore.enamel.processing.demo

import com.benoitthore.enamel.core.math.random
import com.benoitthore.enamel.core.randomColor
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.contains
import com.benoitthore.enamel.geometry.functions.map
import com.benoitthore.enamel.geometry.functions.toCircle
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.processing.KotlinPApplet
import com.benoitthore.enamel.processing.getViewBounds
import com.benoitthore.enamel.processing.mousePosition
import com.benoitthore.enamel.processing.runApplet
import com.benoitthore.enamel.processing.visualentity.drawVE
import com.benoitthore.enamel.processing.visualentity.toProcessing
import com.benoitthore.visualentity.ECircleVisualEntity
import com.benoitthore.visualentity.toVisualEntity

fun main() {
    runApplet<TmpApplet>()
}

class TmpApplet : KotlinPApplet() {

    override fun settings() {
        size(1000, 500)
    }

    override fun setup() {
        super.setup()
        surface.setResizable(true)
    }

    val gameController: IGameController = GameController()


    private val fromFrame = E.Rect(0, 0, 1, 1)
    private val toFrame = E.Rect()
    private val buffer = E.Circle().toVisualEntity {}.toProcessing()
    override fun draw() {
        background(0)

//        val circle = mousePosition()
//            .toCircle(0.1 * width)
//            .toVisualEntity { fillColor = 0xFF_FFFFFF.i }
//            .toProcessing()
//
//        val other = getViewBounds().getCenter().toCircle(0.1 * width)
//        if (!getViewBounds().contains(circle) || other.intersects(circle)) {
//            circle.style.fill!!.color = 0xFF_ff0000.i
//        }
//
//        fill(255)
//        stroke(255)
//        drawVE(other.toVisualEntity { fillColor = 0xFF_BBBBBB.i }.toProcessing())
//        drawVE(circle)
//
//        return
        getViewBounds(toFrame)

        val p = if (mouseButton != 0) {
            mousePosition().normalizeIn(getViewBounds())
        } else {
            null
        }
        gameController.step(p).forEach {
            it.map(fromFrame, toFrame, buffer)
            buffer.style = it.style
            drawVE(buffer)
        }
    }
}


interface IGameController {
    fun step(p: EPoint? = null): List<ECircleVisualEntity>
}

class GameController : IGameController {
    private val circles: MutableList<ECircleVisualEntity> = mutableListOf()
    private val grownCircles: MutableList<ECircleVisualEntity> = mutableListOf()
    private val growingCircles: MutableList<ECircleVisualEntity> = mutableListOf()
    private val frame = E.Rect(0, 0, 1, 1)

    private val increment = 0.001f
    private val minRadius = 0.05f

    init {
        addCircle()
    }

    private fun addCircle(p: EPoint? = null) {
        val p = p ?: E.Point.Random()

        val circle = p.toCircle(random(minRadius))
            .toVisualEntity { fillColor = randomColor() }
        circles.forEach {
            if (circle.intersects(it)) {
                return
            }
        }
        circles += circle
        growingCircles += circle
    }


    override fun step(p: EPoint?): List<ECircleVisualEntity> {
        addCircle(p)
        val remove = mutableListOf<ECircleVisualEntity>()
        growingCircles.forEachIndexed { i, circle ->
            circle.radius += increment
            if (!frame.contains(circle)) {
                remove += circle
//                circle.radius = circle.center.distanceTo(frame)
                return@forEachIndexed
            }
            circles.forEach { other ->
                if (other != circle && other.intersects(circle)) {
                    remove += circle
                    circle.radius = circle.center.distanceTo(other)
                    return@forEachIndexed
                }
            }
        }
        remove.forEach {
            growingCircles -= it
            grownCircles += it
        }
        return circles
    }
}