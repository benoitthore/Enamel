package com.benoitthore.enamel.processing

import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.functions.*
import com.benoitthore.enamel.geometry.primitives.div
import com.benoitthore.enamel.geometry.primitives.minus
import com.benoitthore.enamel.geometry.primitives.point.point
import com.benoitthore.enamel.geometry.primitives.times
import com.benoitthore.enamel.processing.visualentity.drawVE
import com.benoitthore.enamel.processing.visualentity.toProcessing
import com.benoitthore.visualentity.toVisualEntity

fun main() {
    runApplet<TmpApplet>()
}


class TmpApplet : KotlinPApplet() {
    val appletSize = E.Size.Square(800)
    val shape = (appletSize / 4).toRect().diagonalTLBR()

    override fun settings() {
        size(appletSize.width.i, appletSize.height.i)
        shape.selfAlignInside(getViewBounds(), center)
    }

    override fun setup() {
        super.setup()
//        frameRate(2f)
    }

    var speed = 0.0004f
    var scaleFactor = 0.5f
    val rect by lazy {
        getViewBounds().toVisualEntity {
            fillColor = 0xFF_00_00
            strokeWidth = 3
            strokeColor = 0x0096AE
        }
            .toProcessing()
    }
    val circle by lazy { rect.innerCircle() }
    val line by lazy { rect.diagonalTLBR() }

    // TODO Sort out functions
    // TODO Make list of function and behaviour at the same time

    var i = 0f
    override fun draw() {
        background(0)
        stroke(255)
        strokeWeight(2f)
        noFill()


        drawVE(
            circle.toVisualEntity(rect.style).toProcessing()
        )
        drawVE(
            (line.scaleAnchor(0.5)).toVisualEntity(rect.style).toProcessing()
        )
        return


        val colors = listOf(0xFF_00_00, 0xF18E33, 0x438eff)

        i+= 0.005f
        val p = (noise(i) point noise(i-100)) * getViewSize() - (mousePosition() * 0.1)
//        draw(p.toCircle(10))
        circle.setOrigin(-100,100)

        draw(
            // TODO Fix this
            circle//.setOrigin(mousePosition())
        )

//        circle.center.set(mousePosition())

//        listOf(rect, line, circle).forEach {
//            it.setOrigin(mousePosition())
//        }
//        drawVE(rect)
//        draw(circle)
//        draw(line)


    }

}