package com.benoitthore.enamel.android

import android.content.Context
import android.content.res.Resources
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.layout_android.EViewGroup


fun Context.eViewGroup(block: EViewGroup.() -> ELayout): EViewGroup = EViewGroup(
    this
).apply {
    layout = block()
}

val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density).toInt()


