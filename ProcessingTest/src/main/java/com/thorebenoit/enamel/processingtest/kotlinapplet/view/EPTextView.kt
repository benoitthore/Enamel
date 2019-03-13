package com.thorebenoit.enamel.processingtest.kotlinapplet.view

import com.thorebenoit.enamel.kotlin.core.color.black
import com.thorebenoit.enamel.kotlin.core.color.red
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.pushPop


open class EPTextView(applet: KotlinPAppletLambda, var text: String) : EPView(applet) {
    override var isAdded: Boolean = false

    var textColor: Int = black
    var textSize: Float = 20f


    open class TextViewStyle(
        open var borderColor: Int? = null,
        open var backgroundColor: Int? = null
    )

    var textViewStyle: TextViewStyle =
        TextViewStyle()

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
