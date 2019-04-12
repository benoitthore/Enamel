package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.core.threading.CoroutineLock
import com.thorebenoit.enamel.kotlin.core.color.randomColor
import com.thorebenoit.enamel.geometry.figures.size
import com.thorebenoit.enamel.geometry.primitives.EPointMutable
import com.thorebenoit.enamel.geometry.primitives.point
import com.thorebenoit.enamel.geometry.AllocationTracker
import com.thorebenoit.enamel.core.threading.coroutine
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet

class PhysicsRainbowRain : KotlinPApplet() {
    init {
        AllocationTracker.debugAllocations = false
    }


    override fun setup() {
        super.setup()
        frame.isResizable = true
    }

    data class Drop(
        val position: EPointMutable,
        var speed: Float = 0f,
        var dropHeight: Float = 0f,
        var color: Int = 0,
        var thickness: Float = 0f
    )

    private var drops: List<Drop> = mutableListOf()
    private val startLock = CoroutineLock()
    override fun settings() {
        esize = 800 size 800

        fullScreen()

        coroutine {
            startLock.wait()
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
//                deltaTime.print



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
        position.y = com.thorebenoit.enamel.core.math.random(-height * 2, -height / 4f)
        speed = random(1f, 2f)
        dropHeight = random(10f, 20f)
        color = randomColor()
        thickness = com.thorebenoit.enamel.core.math.random(2f, 3f)
        return this
    }

    override fun draw() {
        startLock.unlock()
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