package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge.*
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.digital.ELayoutSerializerDigital
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import com.thorebenoit.enamel.kotlin.core.color.*


object ProcessingTestMain {
    // NOT WORKING LIKE IN EXAMPLE
//            val layout = 3.of { ELayoutLeaf().sizedSquare(100) }
//                .stacked(bottomCenter, 10)
//                .aligned(bottom)
//                .padded(10)

    @JvmStatic
    fun main(args: Array<String>) {

        PlaygroundApplet.start(800, 800) {

            val big = ELayoutLeaf(red)
            val small = ELayoutLeaf(blue)

            val smallLayout = small
                .sizedSquare(200)
                .padded(50)
                .arranged(middle, snugged = true)
                .scaled(y = 2)
                .arranged(topRight)

            val layout = big
                .tracked(smallLayout)
                .height(100)
                .padded(50)
                .aligned(top)

//            val layout = smallLayout





            onDraw {
                layout.arrange(eframe)
                layout.draw()
                small.frame.print
                big.frame.print
                noLoop()
            }
        }

    }

}

