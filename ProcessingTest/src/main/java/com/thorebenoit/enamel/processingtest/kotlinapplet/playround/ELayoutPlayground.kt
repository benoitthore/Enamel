package com.thorebenoit.enamel.processingtest.kotlinapplet.playround

import com.thorebenoit.enamel.kotlin.animations.lerp
import com.thorebenoit.enamel.kotlin.core.color.randomColor
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.arranged
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.layoutTag
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.sized
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.stackedBottomLeft
import com.thorebenoit.enamel.kotlin.geometry.layout.playground.PlaygroundServer
import com.thorebenoit.enamel.kotlin.geometry.layout.playground.sendToPlayground
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutTag
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.ETransition
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.SingleElementAnimator
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


fun main() {
    startEPViewPlayground(
        setOf(
            EPTextView("Label A", "A").apply { textViewStyle.backgroundColor = randomColor() },
            EPTextView("Label B", "B").apply { textViewStyle.backgroundColor = randomColor() },
            EPTextView("Label C", "C").apply { textViewStyle.backgroundColor = randomColor() },
            EPTextView("Label D", "D").apply { textViewStyle.backgroundColor = randomColor() }
        )
    )
    /* EXAMPLE CODE:
    val list = listOf("A", "B", "C", "D")

    list
        .shuffled()
    //    .subList(0, random(list.size).i + 1)
        .layoutTag
        .map {
            it.sized(random(100, 200), 150)
        }
        .stackedBottomRight()
        .arranged(EAlignment.topLeft)
        .sendToPlayground()

     */
}

fun startEPViewPlayground(viewList: Set<EPView>) = PlaygroundApplet.start(400, 800) {

    val viewGroup = EPViewGroup()
    viewGroup.viewList.addAll(viewList)

    val originFrame = ERectType()
    val transition = ETransition<EPView>(
        executeOnUiThread = { coroutine(it) },
        getExitAnimation = { ref, rect ->
            object : SingleElementAnimator<EPView>(ref, rect) {
                val buffer = ERect()
                override fun animateTo(progress: Float) {
                    ref.ref.viewRef.onLayout(buffer.lerp(progress, rect, originFrame))
                }

            }
        },
        getEnterAnimation = { ref, rect ->
            object : SingleElementAnimator<EPView>(ref, rect) {
                val buffer = ERect()
                override fun animateTo(progress: Float) {
                    ref.ref.viewRef.onLayout(buffer.lerp(progress, originFrame, rect))
                }

            }
        }

    )

    PlaygroundServer().start(

        onNewLayout = {
            transition.to(it, eframe)
        }
    )

    frame.isResizable = true

    onDraw {
        background(255)

        viewGroup.onDraw(this)
    }

}
