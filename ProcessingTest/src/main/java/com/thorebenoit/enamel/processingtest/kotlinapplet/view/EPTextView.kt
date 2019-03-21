package com.thorebenoit.enamel.processingtest.kotlinapplet.view

import com.thorebenoit.enamel.kotlin.core.color.black
import com.thorebenoit.enamel.kotlin.core.color.red
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.pushPop


open class EPTextView(applet: KotlinPAppletLambda, var text: String) : EPView(applet) {
    override var isAdded: Boolean = false


    open class TextViewStyle(
       open var textColor: Int = black,
        open var textSize: Float = 20f,
        open var borderColor: Int? = null,
        open var backgroundColor: Int? = null
    )

    var textViewStyle: TextViewStyle =
        TextViewStyle()

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


            fill(textViewStyle.textColor)
            stroke(textViewStyle.textColor)
            textSize(textViewStyle.textSize)

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
