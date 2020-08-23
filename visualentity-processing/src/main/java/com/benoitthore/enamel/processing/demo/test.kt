package com.benoitthore.enamel.processing.demo

import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.alignement.NamedPoint
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.functions.innerCircle
import com.benoitthore.enamel.geometry.functions.inset
import com.benoitthore.enamel.geometry.functions.scaleAnchor
import com.benoitthore.enamel.geometry.functions.setOriginSize
import com.benoitthore.enamel.geometry.selfLerp
import com.benoitthore.enamel.processing.KotlinPApplet
import com.benoitthore.enamel.processing.getViewBounds
import com.benoitthore.enamel.processing.getViewSize
import com.benoitthore.enamel.processing.runApplet
import com.benoitthore.enamel.processing.visualentity.drawVE
import com.benoitthore.enamel.processing.visualentity.toProcessing
import com.benoitthore.enamel.processing.visualentity.withTransform
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

    val runner = DemoRunner(Demos[0])


    var progress = 0f
    override fun draw() {
        background(0)
        stroke(255)


//        if (progress > 1f) {
//            progress = 0f
//        }
//
//
//        val origin = getViewBounds().scaleAnchor(0.25, NamedPoint.topLeft).innerCircle()
//            .toVisualEntity { strokeColor = 0xff0000 ; strokeWidth = 5 }.toProcessing()
//
//        origin.setOriginSize(0,0,125,125)
//        val dest = getViewBounds().scaleAnchor(0.25, NamedPoint.bottomRight).innerCircle()
//            .toVisualEntity { strokeColor = 0x00ff00 ; strokeWidth = 5 }.toProcessing()
//
//
//        val ve = origin
//            .innerCircle()
//            .selfLerp(progress, origin, dest)
//            .toVisualEntity { fillColor = 0xffffff }.toProcessing()
//        drawVE(ve
//            , origin, dest
//        )
//        progress += 0.02f
//
//        return

        val progressBar = E.Rect(
            height = (this.height * 0.005f).coerceAtLeast(1f),
            width = this.width * runner.progress
        )
            .toVisualEntity { fillColor = 0xff0000 }.toProcessing()

        drawVE(progressBar)

        val frame = getViewBounds().inset(top = progressBar.height * 2)

        val visualEntities = runner.step(frame)

        visualEntities.forEach {
            drawVE(it)
        }


    }
}
