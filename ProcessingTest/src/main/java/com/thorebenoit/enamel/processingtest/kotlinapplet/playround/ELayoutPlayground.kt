package com.thorebenoit.enamel.processingtest.kotlinapplet.playround

import com.thorebenoit.enamel.kotlin.animations.lerp
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.playground.PlaygroundServer
import com.thorebenoit.enamel.kotlin.geometry.layout.playground.sendToPlayground
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutTag
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.ETransition
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.SingleElementAnimator
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


private val Number.dp get() = this.toDouble() * 3.0
fun main() {
    startEPViewPlayground(
        mutableSetOf<EPView>().apply {
            ('A'..'Z').map {
                add(EPTextView("Label $it", "$it").apply { textViewStyle.backgroundColor = randomColor() })
            }
        }
    )

    listOf("A", "B", "C").map { it.layoutTag }
        .map {
            it.sizedSquare(random(10, 50).dp)
        }
        .stackedBottomRight(10.dp)              // EStackLayout (align linearly)
//    .snugged()                              // ESnuggingLayout (wrapContent)

        .leaf(red.withAlpha(0.25))
        .padded(64.dp)                          // EPaddingLayout (margins)
        .leaf(blue.withAlpha(0.25))

        .arranged(EAlignment.topRight)                     // EBoxLayout (gravity)
        .sendToPlayground()
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

    val deserializer = ELayoutDeserializer()
    deserializer.addDeserializer(ELayoutTag::class.java) { jsonObject ->
        val tag = jsonObject.getString("tag")
        viewGroup.viewList.first { it.tag == tag }.laidIn(viewGroup)
    }

    PlaygroundServer.start(deserializer) {
        transition.to(it, eframe)
    }


    frame.isResizable = true

    onDraw {
        background(dkGray)

        viewGroup.onDraw(this)
    }

}
