package com.thorebenoit.enamel.android.dsl.views

import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.thorebenoit.enamel.android.dsl.customView

fun ViewGroup.textView(text: CharSequence, init: TextView.() -> Unit = {}) = customView<TextView> {
    this.text = text
    init()
}


fun ViewGroup.button(text: CharSequence, init: TextView.() -> Unit = {}) = customView<Button> {
    this.text = text
    init()
}