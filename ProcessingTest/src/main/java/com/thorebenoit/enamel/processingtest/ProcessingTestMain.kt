package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.pushPop
import processing.core.PApplet
import processing.core.PConstants

private abstract class EPView(val applet: KotlinPAppletLambda) {
    abstract fun draw()
}

private open class EPTextView(applet: KotlinPAppletLambda, var text: String) : EPView(applet) {

    var textColor: Int = black
    var textSize: Float = 20f


    var drawingRect: ERectType = ERectType()


    override fun draw() {
        applet.pushPop {

            fill(red)
//            drawingRect.apply {
//            rectMode(PConstants.CORNER)
//                clip(x, y, width, height)
//                draw()
//            }


            fill(textColor)
            stroke(textColor)
            textSize(textSize)

            text(text, drawingRect.origin.x, textSize + drawingRect.origin.y)
        }
    }
}

private class EPButton(applet: KotlinPAppletLambda, text: String) : EPTextView(applet, text) {
    var borderColor: Int = black

    init {
        var i = 0
        applet.onMouseClicked {
            if (drawingRect.contains(applet.mousePosition)) {
                println("Click! ${i++}")
            }
        }
    }

    override fun draw() {
        applet.pushPop {
            noFill()
            stroke(borderColor)
            drawingRect.draw()
        }

        super.draw()
    }
}

object ProcessingTestMain {

    @JvmStatic
    fun main(args: Array<String>) {
        PlaygroundApplet.start {
            esize = 1000 size 1000
            frame.isResizable = true

            windowLocation = 0 point 0

            val tv = EPTextView(this, "test")

            tv.drawingRect = ERectType(size = 100 size 10)
            val btn = EPButton(this, "Click Me")

            onDraw {
                //                noLoop()
                background(255)
                stroke(0)
                strokeWeight(1f)
                fill(0)

                noFill()

                tv.draw()




//                ELayoutDemo._1 arrangedIn eframe then { draw() }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//
//                listOf(
//                    100 size 100,
//                    200 size 200,
//                    300 size 300
//                ).rectGroupJustified(
//                    alignment = EAlignment.rightCenter,
//                    toFit = width / 2,
//                    position = mousePosition,
//                    anchor = NamedPoint.center,
//                    padding = EOffset(10)
//                )
//                    .apply {
//
//                        noFill()
//
//                        strokeWeight(2f)
//                        stroke(green)
//                        frame.draw()
//
//                        strokeWeight(1f)
//                        stroke(red)
//                        forEach {
//                            it.draw()
//                        }
//                    }
            }
        }
    }

}