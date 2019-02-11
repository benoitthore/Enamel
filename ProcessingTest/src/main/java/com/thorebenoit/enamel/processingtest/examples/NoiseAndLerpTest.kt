package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.kotlin.animations.lerp
import com.thorebenoit.enamel.kotlin.core.colorHSL
import com.thorebenoit.enamel.kotlin.core.math.OpenSimplexNoise
import com.thorebenoit.enamel.kotlin.core.math.Scale
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.time.EDeltaTimer
import com.thorebenoit.enamel.kotlin.core.time.ETimerAnimator
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.innerCircle
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.primitives.degrees
import com.thorebenoit.enamel.processingtest.KotlinPApplet

/**
 * Usage: Click, wait, click again
 */
class NoiseAndLerpTest : KotlinPApplet() {

    override fun setup() {
        super.setup()
        frame.isResizable = true
    }


    override fun settings() {
        super.settings()
        AllocationTracker.debugAllocations = false
        esize = 800 size 800

        onMouseClicked {
            if (polyList.size == 2) {
                animator.duration = 5_000L
                animator.start()
                return@onMouseClicked
            }
            polyList += lastList
        }
    }

    val polyList = mutableListOf<List<EPointType>>()


    var angle = 0.degrees()
    val deltaTimer = EDeltaTimer()
    var noiseProgress = 0.0
    var noiseStep = 0.001
    val noise = OpenSimplexNoise()

    val numberOfPoints = 10
    private lateinit var lastList: List<EPoint>

    override fun draw() {

        if (polyList.size == 2) {
            doLerp()
            return
        }

        background(255)

        stroke(colorHSL(0f))
        strokeWeight(2f)

        noFill()


        deltaTimer.frame { delta ->

            //            angle.offset((-delta).degrees())

            val circle = eframe.innerCircle().inset(width * 0.1)

            fill(128f)
            lastList = circle
                .toListOfPoint(numberOfPoints, angle,
                    distanceList =
//                    emptyList()
                    (0 until numberOfPoints - 2).map { getNoise(it * 0.3) * circle.radius }
                )
                .toList().draw(closed = true)


        }
    }

    val animator = ETimerAnimator()

    private fun doLerp() {
        background(255)
        val copiedList = polyList.first().map { it.toMutable() }

        copiedList.lerp(animator.progress, polyList[0], polyList[1])
        copiedList.draw(true)

        if (animator.progress > 1f) {
            animator.progress = 0f
        }
    }

    fun getNoise(offset: Number = 0f) =
        Scale.map(noise.eval(noiseProgress + offset.f), -1, 1, 0, 1)
            .also { noiseProgress += noiseStep }

}