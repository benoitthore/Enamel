package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.math.d
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.core.time.ETimerAnimator
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRefObject
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.EChangeBoundAnimator
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.ETransition
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.SingleElementAnimator
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.kotlin.threading.coroutineDelayed
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.view.EPTextView
import com.thorebenoit.enamel.processingtest.kotlinapplet.view.EPView
import com.thorebenoit.enamel.processingtest.kotlinapplet.view.EPViewGroup
import com.thorebenoit.enamel.processingtest.kotlinapplet.view.laidIn
import kotlinx.coroutines.runBlocking
import java.util.*


object ProcessingTestMain {


    @JvmStatic
    fun main(args: Array<String>) {

        PlaygroundApplet.start(400, 800) {

            fun createTV() = EPTextView("123").apply {
                textViewStyle.backgroundColor = randomColor()
            }

            val viewGroup = EPViewGroup()

            viewGroup.layout =
                3.of {
                    createTV().laidIn(viewGroup)
                        .sized(300, 150)
                }
                    .stackedBottomLeft()
                    .arranged(EAlignment.topCenter)

            onDraw {
                viewGroup.onLayout(eframe)

                background(255)
                viewGroup.onDraw(this)
            }
        }
    }

}

