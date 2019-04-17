package com.thorebenoit.enamel.android.dsl.views

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.thorebenoit.enamel.android.dsl.createView
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


fun Context.textView(text: CharSequence, init: TextView.() -> Unit = {}) = createView<TextView> {
    this.text = text
    init()
}


fun Context.editText(hint: CharSequence? = null, init: TextView.() -> Unit = {}) = createView<EditText> {
    if (hint != null) {
        this.hint = hint
    }
    init()
}


fun Context.button(text: CharSequence, init: TextView.() -> Unit = {}) = createView<Button> {
    this.text = text
    init()
}