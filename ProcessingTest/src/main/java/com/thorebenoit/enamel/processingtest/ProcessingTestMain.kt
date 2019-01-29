package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.*
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointImmutable
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
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


    override fun setup() {
        super.setup()
        frame.isResizable = true
    }

    override fun settings() {
        super.settings()
        esize = 800 size 800
    }

    override fun draw() {

        stroke(colorHSL(0f))
        strokeWeight(2f)

        noFill()

        val buffer = ERect()
        val rect = eframe.inset(eframe.size.min * 0.25f)
        rect.draw()

        // TESTING Anchor Position
//        ERectAnchorPos(
//            anchor = EAlignment.middle.namedPoint,
//            position = ecenter,
//            size = 100 size 100,
//            buffer = buffer
//        )
//        buffer.draw()
//        fill(colorHSL(.25))
//        stroke(colorHSL(.25))
//        ecenter.toCircle(4, ECircle()).draw()

        EAlignment.all.forEach { alignment ->

            rect
                .rectAlignedOutside(
                    aligned = alignment,
                    spacing = 20,
                    size = 40 size 40
//                    rect.size.copy().scale(0.5f)
//                        .apply {
//                            this.width = min
//                            this.height = min
//                        }
                    , buffer = buffer
                ).draw()
        }


    }


}

