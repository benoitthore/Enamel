package com.thorebenoit.enamel.android.dsl.views

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.thorebenoit.enamel.android.matchParent

inline fun <T : View> T.frameLayoutLP(
    width: Int = matchParent,
    height: Int = matchParent,
    init: FrameLayout.LayoutParams.() -> Unit = {}
): T {
    layoutParams = FrameLayout.LayoutParams(width, height).apply(init)
    return this
}

inline fun <T : View> T.linearLayoutLP(
    width: Int = matchParent,
    height: Int = matchParent,
    init: LinearLayout.LayoutParams.() -> Unit = {}
): T {
    layoutParams = LinearLayout.LayoutParams(width, height).apply(init)
    return this
}