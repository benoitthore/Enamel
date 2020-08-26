package com.benoitthore.enamel.processing.demo

import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.functions.*
import com.benoitthore.enamel.geometry.primitives.div
import com.benoitthore.enamel.processing.KotlinPApplet
import com.benoitthore.enamel.processing.getViewBounds
import com.benoitthore.enamel.processing.mousePosition
import com.benoitthore.enamel.processing.runApplet
import com.benoitthore.enamel.processing.visualentity.drawVE
import com.benoitthore.enamel.processing.visualentity.toProcessing
import com.benoitthore.visualentity.DemoDrawer
import com.benoitthore.visualentity.DemoRunner
import com.benoitthore.visualentity.generic.toVisualEntity
import com.benoitthore.visualentity.style.style

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

    val INDEX = 0


    val drawer =
        DemoDrawer(DemoRunner(com.benoitthore.visualentity.Demos[INDEX])) { toProcessing() }

    var i = 0

    init {

        Thread{
            while (true){
                Thread.sleep(1000L)
                i++
            }
        }.start()
    }
    override fun draw() {
        background(0)

        getViewBounds()

            .run {
                when (i % 4) {
                    0 -> innerCircle()
                    1 -> innerOval()
                    2 -> diagonalTLBR()
                    3 -> toRect()
                    else -> TODO()
                }
            }
            .apply {
                setSize(getSize() / 3f)
            }
            .setCenter(mousePosition())
            .toVisualEntity { strokeWidth = 10f; strokeColor = 0xFF_ffffff.i }
            .toProcessing()

            .drawVE()
            .also {
                val lowerShape = it.copy().selfOffset(y = it.height).drawVE().toRect()

                val style = it.style.copy(border = it.style.border?.copy(width = 1f))

                val rect = it.toRect()
                rect
                    .toVisualEntity(style)
                    .toProcessing()
                    .drawVE()

                lowerShape.toVisualEntity(style).toProcessing().drawVE()

            }

        return

        drawer.getDrawables(getViewBounds()).forEach {
            drawVE(it.toProcessing())
        }

    }
}
