package com.thorebenoit.enamel.android.dsl.views

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.thorebenoit.enamel.android.dsl.EnamelContext

////////////////////
// From ViewGroup //
////////////////////
inline fun ViewGroup.constraintLayout(init: ConstraintLayout.() -> Unit = {}) =
    ConstraintLayout(context).apply { addView(this); init() }

inline fun ViewGroup.frameLayout(crossinline init: FrameLayout.() -> Unit = {}) =
    FrameLayout(context).apply { addView(this); init() }

inline fun ViewGroup.linearLayout(crossinline init: LinearLayout.() -> Unit = {}) =
    LinearLayout(context).apply { addView(this); init() }

inline fun ViewGroup.verticalLayout(crossinline init: LinearLayout.() -> Unit = {}) =
    LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        addView(this)
        init()
    }


//////////////////
// From Context //
//////////////////
inline fun Context.constraintLayout(init: ConstraintLayout.() -> Unit = {}) = ConstraintLayout(this).apply(init)

inline fun Context.frameLayout(crossinline init: FrameLayout.() -> Unit = {}) = FrameLayout(this).apply(init)

inline fun Context.linearLayout(crossinline init: LinearLayout.() -> Unit = {}) = LinearLayout(this).apply(init)

inline fun Context.verticalLayout(crossinline init: LinearLayout.() -> Unit = {}) = LinearLayout(this).apply {
    orientation = LinearLayout.VERTICAL
    init()
}
