package com.benoitthore.enamel.processing

import com.benoitthore.enamel.core.asList
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.functions.inset
import com.benoitthore.enamel.processing.demo.Demos
import com.benoitthore.enamel.processing.visualentity.drawVE
import com.benoitthore.enamel.processing.visualentity.toProcessingGeneric

fun main() {
    runApplet<TmpApplet>()
}

class TmpApplet : KotlinPApplet() {
    val appletSize = E.Size.Square(800)

    override fun settings() {
        size(appletSize.width.i, appletSize.height.i)
    }

    val demos = Demos[1].asList()

    var progress = 0f
    override fun draw() {
        background(0)
        stroke(255)
        if (progress > demos.size) {
            progress = 0f
        }

        val index = progress.toInt()
        val subProgress = progress - index
        val demo = demos[index]

        text("$index: ${demo.name}",0f,g.textSize)

        demo.get(getViewBounds()
            .inset(top = g.textSize * 2)
            , subProgress).map { it.toProcessingGeneric() }.forEach {
            drawVE(it)
        }

        progress += 0.01f

    }
}
