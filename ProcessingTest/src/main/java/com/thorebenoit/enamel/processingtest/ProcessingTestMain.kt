package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge.*
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.digital.ELayoutSerializerDigital
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet


object ProcessingTestMain {


    @JvmStatic
    fun main(args: Array<String>) {

        val slice = ELayoutLeaf()
        val remainder = ELayoutLeaf()

        val layout = slice.aligned(left, of = remainder, spacing = 5)
        val serializer = ELayoutSerializerDigital.createIntIDSerializer { it.newInstance() }
        serializer.serialize(layout)
        serializer.toDeserializer().readLayout()


        return

        PlaygroundApplet.start(800, 800) {

            // NOT WORKING LIKE IN EXAMPLE
//            val layout = 3.of { ELayoutLeaf().sizedSquare(100) }
//                .stacked(bottomCenter, 10)
//                .aligned(bottom)
//                .padded(10)

            val slice = ELayoutLeaf()
            val remainder = ELayoutLeaf()

            val sliceLayout = slice
                .sizedSquare(10)
                .aligned(ERectEdge.top)

            val divideLayout = sliceLayout.aligned(left, of = remainder, spacing = 5)

            val layout = divideLayout
                .scaled(0.75,0.25f)
                .padded(5)
                .arranged(middle)



            onDraw {
                layout.arrange(eframe)
                layout.draw()
            }
        }

    }

}

