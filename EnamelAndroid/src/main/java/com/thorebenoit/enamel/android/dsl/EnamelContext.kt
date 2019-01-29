package com.thorebenoit.enamel.android.dsl

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.thorebenoit.enamel.kotlin.core.f
import com.thorebenoit.enamel.kotlin.core.i

fun Context.enamelContext(init: EnamelContext.() -> View): View = EnamelContext(
    this
).init()

fun ViewGroup.enamelDelegate(init: EnamelContext.() -> Unit) {
    EnamelContext(context).apply(init)
}

open class EnamelContext(val context: Context) {


    val Number.dp get() = (context.resources.displayMetrics.scaledDensity * i).i
    val Number.sp get() = context.resources.displayMetrics.scaledDensity * f

    fun Int.asColor() = ContextCompat.getColor(context, this)
    fun Int.asDrawable() = ContextCompat.getDrawable(context, this)

}

val CharSequence.bold
    get() = withTypeface(Typeface.BOLD)

val CharSequence.italic
    get() = withTypeface(Typeface.ITALIC)


fun CharSequence.withTypeface(typeface: Int) = SpannableStringBuilder(this)
    .apply {
        this.setSpan(StyleSpan(typeface), 0, length, 0)
    }