package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.math.d
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.core.time.ETimerAnimator
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.arranged
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.sizedSquare
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.snugged
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.stacked
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRefObject
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.EChangeBoundAnimator
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.ETransition
import com.thorebenoit.enamel.kotlin.geometry.layout.transition.SingleElementAnimator
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.view.EPTextView
import com.thorebenoit.enamel.processingtest.kotlinapplet.view.EPView
import kotlinx.coroutines.runBlocking


object ProcessingTestMain {


    @JvmStatic
    fun main(args: Array<String>) {

        PlaygroundApplet.start {

            val applet = this
            fun EPTextView.toRef() = ELayoutRefObject(
                this,
                addToParent = { isAdded = true },
                removeFromParent = { isAdded = false },
                isSameView = { other -> this@toRef.text == other.text } // EQUAL FUNCTION
            )
            esize = 1000 size 1000
            val layout1 = 3
                .of {
                    val tv = EPTextView(applet, "$it")
                    tv.textViewStyle.textColor = white
                    tv.textViewStyle.borderColor = white

                    ELayoutRef(
                        tv.toRef(),
                        { size -> size },
                        { rect -> tv.drawingRect.set(rect) })
                }
                .map {
                    it.sizedSquare(100)
                }
                .stacked(EAlignment.bottomCenter)
                .snugged()
                .arranged(EAlignment.middle)


            val layout2 = 3
                .of {
                    val tv = EPTextView(applet, "${it + 1}")
                    tv.textViewStyle.textColor = white
                    tv.textViewStyle.borderColor = white

                    ELayoutRef(
                        tv.toRef(),
                        { size -> size },
                        { rect -> tv.drawingRect.set(rect) })
                }
                .map {
                    it.sizedSquare(100)
                }
                .stacked(EAlignment.bottomCenter)
                .snugged()
                .arranged(EAlignment.leftCenter)




            frame.isResizable = true

            fun textViewFade(enter: Boolean = true): (ELayoutRef<EPView>) -> SingleElementAnimator<EPView> =
                {
                    object : SingleElementAnimator<EPView>(it) {
                        override fun animateTo(progress: Float) {
                            val tv = (ref.ref.viewRef as? EPTextView) ?: return
                            if (enter) {
                                tv.textViewStyle.borderColor = progress.argbEvaluate(0, white)
                                tv.textViewStyle.textColor = progress.argbEvaluate(0, white)
                            } else {
                                tv.textViewStyle.borderColor = progress.argbEvaluate(white, 0)
                                tv.textViewStyle.textColor = progress.argbEvaluate(white, 0)
                            }
                        }
                    }
                }


            val transition: ETransition<EPView> = ETransition<EPView>(
                executeOnUiThread = { coroutine(it) },
                doAnimation = { duration, animator ->
                    val timer = ETimerAnimator()


                    timer.start()
                    while (timer.progress < 1f) {
                        val progress = Math.min(timer.progress.d, 1.0).f
                        animator(progress)
                        delay(8)
                    }
                    animator(1f)

                },
                getEnterAnimation = textViewFade(true),
                getExitAnimation = textViewFade(false),
                getUpdateAnimation = EChangeBoundAnimator.getBuilder()
            )

            var i = 0

            onMouseClicked {
                if (i % 2 == 0)
                    transition.to(layout2, bounds = eframe)
                else
                    transition.to(layout1, bounds = eframe)
                i++
            }

            onDraw {

                background(0)
                fill(255f)
//                layout1.arrange(eframe)
//                layout2.arrange(eframe)

                layout1.draw()
                layout2.draw()
            }
        }
    }

}

