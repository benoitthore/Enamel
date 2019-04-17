package com.thorebenoit.enamel.android

import android.content.Context
import android.content.res.Resources
import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.layout_android.EViewGroup


fun Context.eLayout(block: EViewGroup.() -> ELayout): EViewGroup = EViewGroup(
    this
).apply {
    layout = block()
}

val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density).toInt()
