package com.thorebenoit.enamel.processingtest.doodleclassifier

import com.thorebenoit.enamel.kotlin.core.data.findIndex
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.core.time.ETimer
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PGraphics
import processing.core.PImage


class DoodleTestApplet : KotlinPApplet() {


    var points = mutableListOf<EPointType>()
    var totalPoints = mutableListOf<List<EPointType>>()

    init {

//        val labels = listOf(
//            "airplane",
//            "apple",
//            "banana",
//            "car",
//            "cat",
//            "dog",
//            "eiffel_tower",
//            "fish",
//            "spider",
//            "spoon"
//        )

        val labels = listOf(
            "apple",
            "banana",
            "car",
            "cat",
            "spoon"
        )


        val t = ETimer()
        val nn = DoodleImageExtractor.getNetwork(labels)

        kotlin.io.println(
            "Total setup time: ${t.elapsed / 1000} seconds"
        )
        onKeyPressed {
            if (key == ' ') {
                val image = getImageFromPoints(totalPoints, 28 size 28)
                val guess = nn.feedForward(image)

                val index = guess.findIndex { it == guess.max() }!!

                "This is: ${labels[index]}".print

                kotlin.io.println()

                totalPoints.clear()

            }
            if (key == 'z' && totalPoints.isNotEmpty()) {
                totalPoints.removeAt(totalPoints.size - 1)
            }
        }


        onMouseDragged {
            points.add(mousePosition.copy())
        }

        onMouseReleased {
            totalPoints.add(points)

            points = mutableListOf()
        }

    }


    override fun settings() {
        size(800, 800)
    }


    override fun draw() {
        background(0)
        noFill()
        strokeWeight(2f)

        stroke(255)
        totalPoints.forEach { it.draw(false) }

//        stroke(255f, 0f, 0f)
        points.draw(false)
    }
}

fun main() {
    KotlinPApplet.createApplet<DoodleTestApplet>()
}

fun KotlinPApplet.getImageFromPoints(totalPoints: List<List<EPointType>>, imageSize: ESizeType): List<Float> {
    val ratio = imageSize / esize

    val totalPoints = totalPoints.map { it.map { it.mult(ratio) } }

    val g = createGraphics(imageSize.width.i, imageSize.height.i, PConstants.JAVA2D)

    g.beginDraw()

    with(g) {
        g.stroke(255)
        totalPoints.forEach {
            beginShape()

            it.forEach {
                vertex(it.x, it.y)
            }
            endShape(PConstants.OPEN)
        }
    }

    g.endDraw()

    g.loadPixels()
    return g.pixels.map { (it and 0xFF) / 255f }

}

private fun ESizeType.toPoint() = width point height
private operator fun ESizeType.div(o: ESizeType): ESizeType = this.width / o.width size this.height / o.height
