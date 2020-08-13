package com.benoitthore.enamel.processing

import com.benoitthore.enamel.core.*
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.core.math.map
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.alignement.NamedPoint
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.interfaces.bounds.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.degrees
import com.benoitthore.enamel.geometry.primitives.angle.rotations
import com.benoitthore.enamel.geometry.primitives.div
import com.benoitthore.enamel.geometry.primitives.point.point
import com.benoitthore.enamel.geometry.primitives.size.size
import com.benoitthore.enamel.geometry.toCircle
import com.benoitthore.enamel.geometry.toRect
import processing.core.PApplet
import kotlin.math.cos

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
    var scaleFactor = 0.1f
    override fun draw() {
        background(0)
        i += speed
//        scaleFactor += 0.001f
        scaleFactor = .75f

        val rect = getViewBounds().selfScaleAnchor(scaleFactor, NamedPoint.center)

        noise(i).print
        drawGradient(
            rect,
            listOf(0xFF_00_00, 0xF18E33, 0x438eff),
            noise(i).rotations()
        )

//        noLoop()

//        val square = E.Rect.Square(100)
//        square.selfOffset(10, 10)
//        background(0)
//        stroke(255)
//        val p = mousePosition()
//
//        draw(shape)
    }

}