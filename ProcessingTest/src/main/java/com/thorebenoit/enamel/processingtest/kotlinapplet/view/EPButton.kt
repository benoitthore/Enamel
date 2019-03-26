//package com.thorebenoit.enamel.processingtest.kotlinapplet.view
//
//import com.thorebenoit.enamel.kotlin.core.color.black
//import com.thorebenoit.enamel.kotlin.core.color.ltGray
//import com.thorebenoit.enamel.kotlin.core.color.red
//import com.thorebenoit.enamel.kotlin.core.print
//import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
//import com.thorebenoit.enamel.kotlin.threading.singleThreadCoroutine
//import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda
//
//class EPButton(applet: KotlinPAppletLambda, text: String) : EPTextView(applet, text) {
//
//
//    class ButtonStyle(
//        var hoverColor: Int? = ltGray,
//        var clickedColor: Int? = red,
//        textColor: Int = black,
//        textSize: Float = 20f,
//        borderColor: Int? = null,
//        backgroundColor: Int? = null
//    ) :
//        TextViewStyle(
//            textColor = textColor,
//            textSize = textSize,
//            borderColor = borderColor,
//            backgroundColor = backgroundColor
//        ) {
//
//        override var backgroundColor: Int? = null
//            set(value) {
//                field = value
//                originalBackgroundColor = value
//            }
//        var originalBackgroundColor: Int? = backgroundColor
//            private set
//
//    }
//
//    val buttonStyle: ButtonStyle =
//        ButtonStyle()
//
//    private var isShowingClick = false
//
//    init {
//        textViewStyle.borderColor = black
//
//        // TODO Refactor this code, it's really bad but was useful for testing layouts
//        applet.onMouseMoved {
//            if (isAdded && !isShowingClick) {
//
//                if (drawingRect.contains(applet.mousePosition)) {
//                    textViewStyle.backgroundColor = buttonStyle.hoverColor
//                } else {
//                    textViewStyle.backgroundColor = buttonStyle.originalBackgroundColor
//                }
//            }
//        }
//        applet.onMouseClicked {
//            if (isAdded && !isShowingClick) {
//                if (drawingRect.contains(applet.mousePosition)) {
//                    textViewStyle.backgroundColor = buttonStyle.clickedColor
//                    isShowingClick = true
//                    performClick(applet.mousePosition.copy())
//                    singleThreadCoroutine {
//                        Thread.sleep(1000)
//                        textViewStyle.backgroundColor = buttonStyle.originalBackgroundColor
//                        isShowingClick = false
//                    }
//
//                } else {
//                    textViewStyle.backgroundColor = buttonStyle.originalBackgroundColor
//                }
//            }
//        }
//    }
//
//    private fun performClick(position: EPointType) {
//        position.print
//    }
//
//}
