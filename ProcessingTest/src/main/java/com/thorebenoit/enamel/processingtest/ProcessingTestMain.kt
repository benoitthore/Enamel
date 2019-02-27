package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.layout.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.processingtest.examples.*
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet


object ProcessingTestMain {

    @JvmStatic
    fun main(args: Array<String>) {

        PlaygroundApplet.start {
            esize = 800 size 800
            frame.isResizable = true

            windowLocation = 0 point 0

            onDraw {

                noLoop()

                background(255)
                noFill()
                stroke(0)
                fill(0)

                val layout =
//                    EPaddingLayout(
//                        EDivideLayout(
//                            EPlaceHolderLayout(red),
//                            EPlaceHolderLayout(green),
//                            EDivideLayout.Division.Fraction(0.5),
//                            ERectEdge.left,
//                            spacing = 10
//                        ),
//                        EOffset(10)
//                    )
                    EStackLayout(
                        listOf(
                            100 size 100 to red,
                            100 size 100 to green,
                            100 size 100 to blue
                        ).map { (size, color) -> size.toPlaceHolder(color) },
                        EAlignment.topRight
                    )

                layout.arrange(eframe)
                layout.draw()


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//                listOf(
//                    100 size 100,
//                    200 size 200,
//                    300 size 300
//                ).rectGroup(
//                    EAlignment.rightCenter, spacing = 10, padding = EOffset(top = 124)
//                    , anchor = NamedPoint.center, position = mousePosition
//                )
//                    .apply {
//
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