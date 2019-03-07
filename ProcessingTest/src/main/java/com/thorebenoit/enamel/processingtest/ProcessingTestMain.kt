package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.backingfield.ExtraValueHolder
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.core.randomString
import com.thorebenoit.enamel.kotlin.core.tryAndForget
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRefObject
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.kotlin.threading.singleThreadCoroutine
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.pushPop
import kotlinx.coroutines.delay
import processing.core.PApplet
import processing.core.PConstants
import java.lang.Exception
import java.time.format.TextStyle


val <T : EPView> T.layoutRef: ELayoutRef<T> by ExtraValueHolder {
    ELayoutRef(
        ELayoutRefObject(this, { isAdded = true }, { isAdded = false }),
        { size -> size },
        { frame -> drawingRect.set(frame) },
        emptyList()
    )
}

abstract class EPView(val applet: KotlinPAppletLambda) {
    abstract var isAdded: Boolean
    open var drawingRect: ERect = ERect()
    fun draw() {
        if (!isAdded) {
            return
        }
        onDraw()
    }

    abstract fun onDraw()
}

private open class EPTextView(applet: KotlinPAppletLambda, var text: String) : EPView(applet) {
    override var isAdded: Boolean = false

    var textColor: Int = black
    var textSize: Float = 20f


    open class TextViewStyle(
        open var borderColor: Int? = null,
        open var backgroundColor: Int? = null
    )

    var textViewStyle: TextViewStyle = TextViewStyle()

    init {
        textViewStyle.borderColor = red
    }

    override fun onDraw() {

        applet.pushPop {

            drawingRect.apply {
                textViewStyle.borderColor?.let { borderColor ->
                    stroke(borderColor)
                } ?: noStroke()

                textViewStyle.backgroundColor?.let { backgroundColor ->
                    fill(backgroundColor)
                } ?: noFill()

                draw()
            }


            fill(textColor)
            stroke(textColor)
            textSize(textSize)

            text(
                text,
                drawingRect.origin.x,
                drawingRect.origin.y,
                drawingRect.width,
                drawingRect.height
            )
        }
    }
}

private class EPButton(applet: KotlinPAppletLambda, text: String) : EPTextView(applet, text) {


    class ButtonStyle(
        var hoverColor: Int? = ltGray,
        var clickedColor: Int? = red,
        borderColor: Int? = null,
        backgroundColor: Int? = null
    ) :
        TextViewStyle(borderColor, backgroundColor) {

        override var backgroundColor: Int? = null
            set(value) {
                field = value
                originalBackgroundColor = value
            }
        var originalBackgroundColor: Int? = backgroundColor
            private set

    }

    val buttonStyle: ButtonStyle = ButtonStyle()

    private var isShowingClick = false

    init {
        textViewStyle.borderColor = black

        // TODO Refactor this code, it's really bad but was useful for testing layouts
        applet.onMouseMoved {
            if (isAdded && !isShowingClick) {

                if (drawingRect.contains(applet.mousePosition)) {
                    textViewStyle.backgroundColor = buttonStyle.hoverColor
                } else {
                    textViewStyle.backgroundColor = buttonStyle.originalBackgroundColor
                }
            }
        }
        applet.onMouseClicked {
            if (isAdded && !isShowingClick) {
                if (drawingRect.contains(applet.mousePosition)) {
                    textViewStyle.backgroundColor = buttonStyle.clickedColor
                    isShowingClick = true
                    performClick(applet.mousePosition.copy())
                    singleThreadCoroutine {
                        Thread.sleep(1000)
                        textViewStyle.backgroundColor = buttonStyle.originalBackgroundColor
                        isShowingClick = false
                    }

                } else {
                    textViewStyle.backgroundColor = buttonStyle.originalBackgroundColor
                }
            }
        }
    }

    private fun performClick(position: EPointType) {
        position.print
    }

}

object ProcessingTestMain {

    @JvmStatic
    fun main(args: Array<String>) {
        PlaygroundApplet.start {
            esize = 1000 size 1000
            frame.isResizable = true

            windowLocation = 0 point 0

            val tv = EPTextView(
                this,
                "Draws text to the screen. Displays the information specified in the first parameter on the screen in the position specified by the additional parameters. A default font will be used unless a font is set with the textFont() function and a default size will be used unless a font is set with textSize(). Change the color of the text with the fill() function. The text displays in relation to the textAlign() function, which gives the option to draw to the left, right, and center of the coordinates.\n "
            )

//            tv.drawingRect = ERect(size = 100 size 500)
//            val btn = EPButton(this, "Click Me")

            val layout =

                (
                        (3.of {
                            EPTextView(this, ('a'..'z').randomString(5)).layoutRef
                        })
                                +
                                (3.of {
                                    EPButton(this, ('a'..'z').randomString(5)).layoutRef
                                })
                        )
                    .mapIndexed { i, layout ->
                        layout.sizedSquare((i + 1) * 50)
                    }
                    .stacked(EAlignment.rightTop, spacing = 10)
                    .snugged()
                    .arranged(EAlignment.topLeft)
                    .padded(20)

            onDraw {
                //                noLoop()
                background(255)
                stroke(0)
                strokeWeight(1f)
                fill(0)

                fill(red)

//                layout.arrange(eframe.offset(mousePosition))
                layout.arrange(eframe)
                layout.draw()

//                tv.drawingRect.origin.set(mousePosition)
//
//                tv.draw()


//                ELayoutDemo._1 arrangedIn eframe then { draw() }

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