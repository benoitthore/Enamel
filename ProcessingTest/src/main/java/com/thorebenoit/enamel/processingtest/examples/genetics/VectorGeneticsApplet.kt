package com.thorebenoit.enamel.processingtest.examples.genetics

import com.thorebenoit.enamel.core.math.Scale
import com.thorebenoit.enamel.core.math.œ
import com.thorebenoit.enamel.core.print
import com.thorebenoit.enamel.kotlin.ai.genetics.DnaBuilder
import com.thorebenoit.enamel.kotlin.ai.genetics.Genome
import com.thorebenoit.enamel.kotlin.ai.genetics.Population
import com.thorebenoit.enamel.geometry.alignement.NamedPoint
import com.thorebenoit.enamel.geometry.figures.ERectCorners
import com.thorebenoit.enamel.geometry.figures.ERectType
import com.thorebenoit.enamel.geometry.primitives.EAngle
import com.thorebenoit.enamel.geometry.primitives.EPoint
import com.thorebenoit.enamel.geometry.primitives.point
import com.thorebenoit.enamel.geometry.primitives.rotation
import com.thorebenoit.enamel.geometry.toCircle
import com.thorebenoit.enamel.core.threading.coroutine
import com.thorebenoit.enamel.geometry.toRect
import com.thorebenoit.enamel.processingtest.examples.steering.SteeringVehicle
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.pushPop
import com.thorebenoit.enamel.processingtest.kotlinapplet.toEPoint
import java.awt.event.KeyEvent
import kotlin.math.pow

class VectorGeneticsApplet : KotlinPApplet() {

    data class DnaV(val steeringVehicle: SteeringVehicle, val steeringData: List<EAngle>)

    val obstacles: MutableList<ERectType> = mutableListOf()

    val target = (200 point 200).toCircle(100)


    private fun SteeringVehicle.resetPosition() {
        position.set(esize.toRect().pointAtAnchor(NamedPoint.bottomCenter))
    }

    private fun SteeringVehicle.distance() = position.distanceTo(target)

    // Fitness Function
    private val evaluateFitness: (Genome<DnaV>) -> Float = { genome ->
        val vehicle = genome.individual.steeringVehicle

        vehicle.resetPosition()


        var nbOfSteps = 0
        var reached = false
        var collided = false
        genome.individual.steeringData.forEachIndexed { i, it ->

            if (reached || collided) {
                return@forEachIndexed
            }
            vehicle.controller.steerAngle(it)
            vehicle.body.update()
            obstacles.forEach { if (it.contains(vehicle.position)) collided = true }
            if (vehicle.distance() < œ) {
                reached = true
                nbOfSteps = i
            }

        }
        var distance = vehicle.distance()

        if (collided) {
            distance *= 2
        }

        val fitnessWhenReached = 1f
        val maxFitnessWhenReached = 100f
        if (reached) {
            Scale.map(nbOfSteps, 0, genome.dna.size, maxFitnessWhenReached, fitnessWhenReached)
        } else {
            (1f / (distance + 1)).pow(3)
        }

    }

    // Population
    val steerPop = Population<DnaV>(
        dnaSize = 750,
        populationSize = 1000,
        mutationRate = 0.075f,
        builder = DnaBuilder { floats -> DnaV(SteeringVehicle(), floats.map { it.rotation() }) },
        evaluateFitness = evaluateFitness
    )


    var currentVehicles: List<DnaV> = listOf()
    var nextGeneration = true
    var skipGen = 0

    init {

        var startDrag: EPoint? = null
        onMousePressed {
            startDrag = it.toEPoint()
        }
        onMouseReleased {
            val rect = ERectCorners(startDrag!!, it.toEPoint())
            obstacles.add(rect)
            startDrag = null
        }


        onKeyPressed {
            if (keyCode == KeyEvent.VK_SPACE) {
                skipGen += 50
                "skipping $skipGen".print
            }
            if (key == 'c') {
                skipGen = 0
            }

        }

    }

    override fun draw() {
        background(255)
        if (nextGeneration) {
            background(255f, 0f, 0f)
            steerPop.evolve()
            println("${steerPop.generationCount} ")

            var _skipGen = Math.min(10, skipGen)
            var skipping = _skipGen > 0
            while (_skipGen > 0) {
                steerPop.evolve()
                skipGen--
                _skipGen--
                println("${steerPop.generationCount} ")
            }
            if (skipping) {
                return
            }

            nextGeneration = false
            accelerateDraw()
            println()


        }

        currentVehicles.forEach { it.steeringVehicle.draw() }


        obstacles.forEach { it.draw() }

        pushPop {
            fill(0f, 255f, 0f)
            target.draw()
        }

    }


    fun accelerateDraw() {

        coroutine {

            currentVehicles = steerPop.individuals.map { it.individual }

            currentVehicles.doPhysics {
                Thread.sleep(500)
            }

            currentVehicles = listOf(steerPop.best.first.individual)

            currentVehicles.doPhysics {
                Thread.sleep(500)
                nextGeneration = true
            }
        }
    }

    fun List<DnaV>.doPhysics(onComplete: () -> Unit) {
        forEach { it.steeringVehicle.resetPosition() }

        for (i in 0 until steerPop.dnaSize) {

            forEach { (vehicle, steeringData) ->
                var collided = false
                obstacles.forEach { if (it.contains(vehicle.position)) collided = true }
                if (collided) {
                    return@forEach
                }
                if (vehicle.distance() > œ) {
                    val steering = steeringData[i]
                    vehicle.controller.steerAngle(steering)
                    vehicle.body.update()
                } else {
                    onComplete()
                    return
                }
            }
            Thread.sleep(1)
        }

        onComplete()
    }

    fun SteeringVehicle.draw() {
        pushPop {
            noStroke()
            fill(color)
            position.toCircle(radius).draw()
        }
    }

}