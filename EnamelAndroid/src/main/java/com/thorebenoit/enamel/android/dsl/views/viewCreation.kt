package com.thorebenoit.enamel.android.dsl.views

import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.thorebenoit.enamel.android.dsl.customView

fun ViewGroup.textView(text: CharSequence, init: TextView.() -> Unit = {}) = customView<TextView> {
    this.text = text
    init()
}


fun ViewGroup.editText(hint: CharSequence? = null, init: TextView.() -> Unit = {}) = customView<EditText> {
    if (hint != null) {
        this.hint = hint
    }
    init()
}


fun ViewGroup.button(text: CharSequence, init: TextView.() -> Unit = {}) = customView<Button> {
    this.text = text
    init()
}