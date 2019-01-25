package com.thorebenoit.enamel.android.dsl.views

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout

inline fun ViewGroup.constraintLayout(init: ConstraintLayout.() -> Unit) =
    ConstraintLayout(context).addAndInit(this, init)

inline fun ViewGroup.frameLayout(crossinline init: FrameLayout.() -> Unit) = FrameLayout(context).addAndInit(this, init)

inline fun ViewGroup.linearLayout(crossinline init: LinearLayout.() -> Unit) =
    LinearLayout(context).addAndInit(this, init)

inline fun ViewGroup.verticalLayout(crossinline init: LinearLayout.() -> Unit) = LinearLayout(context).apply {
    orientation = LinearLayout.VERTICAL
    addAndInit(this@verticalLayout, init)
}


/////////
inline fun <T : View> T.addAndInit(layout: ViewGroup, init: T.() -> Unit) = apply {
    layout.addView(this)
    init()
}
