package com.thorebenoit.enamel.processingtest.kotlinapplet.playround

import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.playground.PlaygroundServer
import com.thorebenoit.enamel.kotlin.geometry.layout.playground.sendToPlayground
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType

val _layout = 3.of { ELayoutLeaf() }
    .mapIndexed { i, layout ->
        layout.sizedSquare((i + 1) * 100)
    }
    .stacked(EAlignment.rightTop, spacing = 10)
    .snugged()
    .arranged(EAlignment.topLeft)
    .padded(20)


//val _layout = ELayoutLeaf().arranged(EAlignment.middle)

class ELayoutPlayground : KotlinPAppletLambda() {

    private var layout: ELayout = _layout

    init {

        PlaygroundServer().start {
            this.layout.print
            it.print
            this.layout = it
        }

        onDraw {
            background(255)
            layout.arrange(eframe)
            layout.draw()
        }
    }


}


fun main() {
//
//    KotlinPApplet.createApplet<ELayoutPlayground>(800 size 800)
//
//    _layout.sendToPlayground()

    PlaygroundApplet.start(800, 800) {

        onDraw {
            background(255)
            noFill()
            stroke(red)
            val rect = eframe.inset(100).draw()
            EAlignment.all.forEach {
                rect.rectAlignedOutside(it, ESizeType.square(20), spacing = 10).draw()
            }

        }

    }

}
