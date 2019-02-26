package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.NamedPoint
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import java.awt.Color


object ProcessingTestMain {

    @JvmStatic
    fun main(args: Array<String>) {
        PlaygroundApplet.start {
            esize = 800 size 800
            frame.isResizable = true

            windowLocation = EPointType.zero

            onDraw {
                background(255)

                listOf(
                    100 size 100,
                    200 size 200,
                    300 size 300
                ).rectGroup(
                    EAlignment.rightCenter, spacing = 10, padding = EOffset(top = 10, left = 10)
                    , anchor = NamedPoint.center, position = mousePosition
                )
                    .rects.apply {
                    noFill()

                    forEach {
                        it.draw()
                    }
                }


            }
        }
    }


}