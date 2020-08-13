package com.benoitthore.enamel.processing

import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.interfaces.bounds.*
import com.benoitthore.enamel.geometry.primitives.div
import com.benoitthore.enamel.geometry.toCircle
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

    override fun draw() {
        val square = E.Rect.Square(100)
        square.selfOffset(10, 10)
        background(0)
        stroke(255)
        val p = mousePosition()

        draw(shape)
    }

}