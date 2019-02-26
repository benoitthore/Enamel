package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.alignement.NamedPoint
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.layout.primitives.EDivideLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.primitives.EPaddingLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.primitives.EPlaceHolderLayout
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

            onDraw {

                noLoop()

                background(255)
                noFill()
                stroke(0)
                fill(0)

                val layout =
                    EPaddingLayout(
                        EDivideLayout(
                            EPlaceHolderLayout(red),
                            EPlaceHolderLayout(green),
                            EDivideLayout.Division.Distance(10),
                            ERectEdge.left,
                            spacing = 10
                        ),
                        EOffset(10)
                    )

                // This works
//                    EPaddingLayout(
//                        EPaddingLayout(
//                            EPlaceHolderLayout(),
//                            EOffset(left = 100)
//                        ),
//                        EOffset(10)
//                    )

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