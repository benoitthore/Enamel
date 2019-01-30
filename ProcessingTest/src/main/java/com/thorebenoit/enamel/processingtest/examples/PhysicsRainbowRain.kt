package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.kotlin.core.randomColor
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.processingtest.KotlinPApplet

class PhysicsRainbowRain : KotlinPApplet() {


    override fun setup() {
        super.setup()
        frame.isResizable = true
    }

    data class Drop(
        val position: EPoint,
        var speed: Float = 0f,
        var dropHeight: Float = 0f,
        var color: Int = 0,
        var thickness: Float = 0f
    )

    private var drops: List<Drop> = mutableListOf()
    override fun settings() {
        esize = 800 size 800

        fullScreen()

        coroutine {
            kotlinx.coroutines.delay(200)
            reGenerateDrops()

            val targetLoopTime = 5f
            var deltaTime = 1f
            while (true) {

                val loopStartedAt = System.currentTimeMillis()

                drops.forEach {
                    val (point, speed, dropHeight, color, thickness) = it

                    point.y += speed * deltaTime
                    if (point.y > height) {
                        it.reset()
                    }

                }


                // Uncomment to introduce fake physics delay
//                kotlinx.coroutines.delay((targetLoopTime * 20).i)

                var currentLoopTime = System.currentTimeMillis() - loopStartedAt
                val sleepNeeded = targetLoopTime - currentLoopTime
                if (sleepNeeded > 0) {
                    kotlinx.coroutines.delay(sleepNeeded.toLong())
                }

                currentLoopTime = System.currentTimeMillis() - loopStartedAt
                deltaTime = currentLoopTime / targetLoopTime
                deltaTime.print



            }
        }
    }

    private fun reGenerateDrops() {
        val dropsPerCol = 10
        drops = (0..width * dropsPerCol).map { x ->
            Drop(x / dropsPerCol point 0f).reset()
        }
    }


    private fun Drop.reset(): Drop {
        position.y = com.thorebenoit.enamel.kotlin.core.random(-height * 2, -height / 4f)
        speed = random(1f, 2f)
        dropHeight = random(10f, 20f)
        color = randomColor()
        thickness = com.thorebenoit.enamel.kotlin.core.random(0.5, 2.5f)
        return this
    }

    override fun draw() {
        background(0)
        noFill()

        drops.forEach { drop ->


            val (point, speed, dropHeight, color, thickness) = drop

            val x = point.x
            val y = point.y

            strokeWeight(thickness)
            stroke(color)
            line(x, y, x, y + dropHeight)


        }



    }


}