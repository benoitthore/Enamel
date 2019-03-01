package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.alignement.NamedPoint
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.layout.*
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.fitting
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.stackedRightCenter
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.stackedTopLeft
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.withSize
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.div
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.kotlin.geometry.primitives.times
import com.thorebenoit.enamel.processingtest.examples.*
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import org.intellij.lang.annotations.Language


object ProcessingTestMain {

    @JvmStatic
    fun main(args: Array<String>) {


        PlaygroundApplet.start {
            esize = 1000 size 1000

            onDraw {

                background(255)

                noFill()

                val rectangle1 = ERect(0, 0, 200, 200)


                val rectangle2 = ERect(200, 200, 200, 200)

                if(rectangle2.contains(mousePosition)){
                    background(green)
                }

                if(rectangle1.contains(mousePosition)){
                    background(black)
                }


                stroke(red)
                rectangle1.draw()

                stroke(blue)
                rectangle2.draw()


            }
        }
        return

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


//                val layout = EBarLayout((100 size 100).toPlaceHolder(red), EAlignment.topLeft)

                val layout = 3.of { EPlaceHolderLayout() }
                    .withSize(100, 50)
                    .stackedRightCenter()
                layout.arrange(eframe)
                layout.draw()


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