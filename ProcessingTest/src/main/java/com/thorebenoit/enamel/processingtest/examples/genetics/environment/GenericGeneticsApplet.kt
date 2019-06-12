package com.thorebenoit.enamel.processingtest.examples.genetics.environment

import com.thorebenoit.enamel.core.math.*
import com.thorebenoit.enamel.core.of
import com.thorebenoit.enamel.core.threading.CoroutineLock
import com.thorebenoit.enamel.geometry.figures.*
import com.thorebenoit.enamel.geometry.primitives.*
import com.thorebenoit.enamel.geometry.toCircle
import com.thorebenoit.enamel.kotlin.ai.genetics.DnaBuilder
import com.thorebenoit.enamel.kotlin.ai.genetics.Population
import com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.ToyNeuralNetwork
import com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.dnaBuilder
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.physics.core.PhysicsBody
import com.thorebenoit.enamel.processingtest.examples.genetics.environment.example.*
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.pushPop
import java.util.*


// TODO
/**TODO
 * If we take this applet and remove the movement, drawing and physics loop(evaluateFitness)
 * then it becomes an easy way to make small creatures evolve
 */
class GenericGeneticsApplet : KotlinPAppletLambda() {



    init {

        onKeyTyped {
            when (it.key) {
                ' ' -> {
                    println(" ")
                }
            }
        }

        onSetup {
            //            vehicles.forEach { vehicle ->
//                vehicle.body.position.set(eframe.center())
//            }
        }

        onDraw {
            background(0)
        }
    }


}


object CarDrivingMain {

    @JvmStatic
    fun main(args: Array<String>) {

        KotlinPApplet.createApplet<GenericGeneticsApplet>(800, 800).apply {
            onSetup {
                frame.isResizable = true
            }
        }
    }
}