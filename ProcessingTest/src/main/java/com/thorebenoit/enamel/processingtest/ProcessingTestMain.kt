package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.colorHSL
import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.NamedPoint
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
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

private operator fun EPointType.div(n: Number) = EPoint(x / n.f, y / n.f)

///
class MainApplet : KotlinPApplet() {


    override fun setup() {
        super.setup()
        frame.isResizable = true
    }


    override fun settings() {
        super.settings()
//        esize = 800 size 800
        esize = allocate { 400 size 400 }

    }

    //    val sizesList = List(4) { ESize(random(10, 50), random(10, 50)) }

    val buffer = allocate { ERect() }
    val _size = allocate { 40 size 40 }
    val bufferCircle = allocate { ECircle() }

    val sizes = allocate { List(5) { ESize(40, 40) } }

    override fun draw() {

        AllocationTracker.debugAllocations = false

        background(255)

        stroke(colorHSL(0f))
        strokeWeight(2f)

        noFill()



        val padding = EOffset(top = 20f)
        val position = allocate { center.draw() }
//        val position = mousePosition
        sizes.rectGroup(
            alignment = EAlignment.leftCenter,
            anchor = NamedPoint.center,
            spacing = 30,
            padding = padding,
            position = position
        ).apply {

            rects.union().selfPadding(padding).draw()
            rects.forEach {
                it.draw()
            }

            fill(0f,0f,255f )
            origin.draw()
        }


        return
        val frameRect = eframe.inset(100)
        frameRect.draw()

        EAlignment.all.forEach { alignment ->

            frameRect.rectAlignedOutside(
                aligned = alignment,
                spacing = 20,
                size = _size,
                buffer = buffer
            )

            buffer.draw()
        }

//        center.offset(10).toCircle(10, bufferCircle).draw()

    }


}

