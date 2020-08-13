package com.benoitthore.enamel.processing

import com.benoitthore.enamel.core.*
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.alignement.NamedPoint
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.interfaces.bounds.*
import com.benoitthore.enamel.geometry.primitives.angle.rotations
import com.benoitthore.enamel.geometry.primitives.div
import com.benoitthore.enamel.geometry.toRect

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

    var i = 0f
    var speed = 0.0004f
    var scaleFactor = 0.5f
    override fun draw() {
        background(0)

        val colors = listOf(0xFF_00_00, 0xF18E33, 0x438eff)

        val rect = getViewBounds().selfScaleAnchor(scaleFactor, NamedPoint.center)
        val circle = rect.innerCircle()
        radialGradient(circle, colors)

        noLoop()

    }

}