package com.benoitthore.enamel.layout.android

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.functions.selfPadding
import com.benoitthore.enamel.geometry.functions.setOriginSize

open class EFrameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val frame: ERect get() = _frame
    private var _frame: ERect = Rect()

    val paddedFrame: ERect get() = _paddedFrame
    private var _paddedFrame: ERect = Rect()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        _frame.setBounds(this)
        _paddedFrame.setBoundsWithPadding(this)
    }
}

fun View.getViewBounds(target: ERect = Rect()) = target.setBounds(this)

// Use these functions if you don't want to extend EFrameView
fun ERect.setBounds(view: View): ERect = apply { setOriginSize(0, 0, view.width, view.height) }
fun ERect.setBoundsWithPadding(view: View): ERect =
    setBounds(view)
        .selfPadding(
            left = view.paddingLeft,
            right = view.paddingRight,
            top = view.paddingTop,
            bottom = view.paddingBottom
        )
