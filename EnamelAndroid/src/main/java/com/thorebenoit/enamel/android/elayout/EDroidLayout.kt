package com.thorebenoit.enamel.android.elayout

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import com.thorebenoit.enamel.android.dsl.views.backgroundColor
import com.thorebenoit.enamel.android.dsl.views.textColor
import com.thorebenoit.enamel.android.dsl.views.textView
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout

class EDroidLayout : ViewGroup {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        backgroundColor = black
        Color.LTGRAY
        setWillNotDraw(false)

        startServer()
    }

//    val tv1 = textView("AA") {
//        backgroundColor = red
//        textSize = 20f
////        gravity = Gravity.CENTER
//        textColor = white
//        tag = "A"
//    }
//    val tv2 = textView("BBBB") {
//        backgroundColor = green
//        textSize = 20f
//        textColor = black
////        gravity = Gravity.CENTER
//        tag = "B"
//    }
//    val tv3 = textView("CCCCCCCC") {
//        backgroundColor = blue
//        textSize = 20f
//        textColor = white
////        gravity = Gravity.CENTER
//        tag = "C"
//    }
    //    val viewList = listOf(tv1, tv2, tv3)

    val viewList = (0..6)
        .map {
            val _tag = 'A' + it
            val i = (it + 1) * 2
            val str = (0 until i).map { _tag }.joinToString(separator = "")

            textView(str) {
                backgroundColor = blue
                textSize = 20f
                textColor = white
                tag = _tag.toString()
            }
        }


    private val transition = androidDefaultTransition()


    fun goToLayout(layout: ELayout) {
        transition.to(layout, eframe.copy())
//        layout.arrange(eframe)
    }

    val eframe: ERectType get() = _eframe
    private var _eframe: ERect = ERect()

    val paddedFrame: ERectType get() = _paddedFrame
    private var _paddedFrame: ERect = ERect()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val left = 0f
        val top = 0f
        val right = r.toFloat() - l
        val bottom = b.toFloat() - t

        _eframe.setSides(
            left = left,
            top = top,
            right = right,
            bottom = bottom
        )

        _eframe.padding(
            left = paddingLeft,
            right = paddingRight,
            top = paddingTop,
            bottom = paddingBottom,
            buffer = _paddedFrame
        )

        // TODO Handle with transition
    }


}

