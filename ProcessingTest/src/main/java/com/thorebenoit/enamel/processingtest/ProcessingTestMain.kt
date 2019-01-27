package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.f
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointImmutable
import com.thorebenoit.enamel.kotlin.geometry.primitives.degrees
import com.thorebenoit.enamel.kotlin.geometry.toCircle
import processing.core.PApplet


object ProcessingTestMain {
    @JvmStatic
    fun main(args: Array<String>) {
        PApplet.main(MainApplet::class.java)
    }

}

/*
These function are used to help drawing only, they shouldn't be put in the library yet
 */
private operator fun ESizeImmutable.div(n: Number) = ESize(width / n.f, height / n.f)

private operator fun EPointImmutable.div(n: Number) = EPoint(x / n.f, y / n.f)
///
class MainApplet : KotlinPApplet() {


    override fun settings() {
        esize = 800 size 800
    }

    override fun draw() {
        background(255)

        with(RotatingCircle){
            play()
        }
        stroke(255f,0f,0f)
        noFill()

        val rect = eframe

        rect.inset(100)
            .pointAtAnchor(.5, .5, EPoint())
            .toCircle(rect.size.min / 2, ECircle())
            .draw()


        rect.draw()

    }


}

object RotatingCircle {

    private val circle = ECircle()
    private val points = MutableList(10) { EPoint() }
    private val circles = points.toCircle(0f)
    private var startAt = 0.degrees()
    private var increment = 1.degrees()

    fun KotlinPApplet.play() {
        fill(255f, 0f, 0f)

        eframe.innerCircle(circle)
            .insetBy(100)
            .draw()
            .pointsInList(points, startAt)
            .toCircle(circle.radius * 0.25f, circles)
            .forEach {
                it.draw()
            }



        startAt += increment
    }
}