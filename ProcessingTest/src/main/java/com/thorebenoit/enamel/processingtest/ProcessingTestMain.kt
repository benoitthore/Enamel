package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.layout.EPlaceHolderLayout
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*


object ProcessingTestMain {

    @JvmStatic
    fun main(args: Array<String>) {


        PlaygroundApplet.start {
            esize = 1000 size 1000
            frame.isResizable = true

            windowLocation = 0 point 0

            onDraw {
                noLoop()
                background(255)
                noFill()
                stroke(0)
                fill(0)




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