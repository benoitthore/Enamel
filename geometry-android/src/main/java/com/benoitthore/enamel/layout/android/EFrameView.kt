package com.benoitthore.enamel.layout.android

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.benoitthore.enamel.geometry.e.E
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ERectMutable

open class EFrameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val frame: ERect get() = _frame
    private var _frame: ERectMutable = E.mrect()

    val paddedFrame: ERect get() = _paddedFrame
    private var _paddedFrame: ERectMutable = E.mrect()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        _frame.setBounds(this)
        _paddedFrame.setBoundsWithPadding(this)
    }
}

// Use these functions if you don't want to extend EFrameView
fun ERectMutable.setBounds(view: View): ERectMutable = apply { set(0, 0, view.width, view.height) }
fun ERectMutable.setBoundsWithPadding(view: View): ERectMutable =
    setBounds(view)
        .padding(
            left = view.paddingLeft,
            right = view.paddingRight,
            top = view.paddingTop,
            bottom = view.paddingBottom
        )
