package com.benoitthore.enamel.layout.android

import android.content.Context
import android.content.res.Resources
import android.view.View
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.refs.ELayoutRef

fun Context.eViewGroup(block: EViewGroup.() -> ELayout): EViewGroup = EViewGroup(
    this
).apply {
    transitionTo(block(), false)
}

inline val Number.dp: Float
    get() = toFloat() * Resources.getSystem().displayMetrics.density


fun <T : View> T.withTag(tag: Any): T = apply {
    this.tag = tag
}

fun <T : View> ELayoutRef<T>.withTag(tag: Any): ELayoutRef<T> = apply {
    this.ref.viewRef.tag = tag
}


inline val <T : View> ELayoutRef<T>.view get() = ref.viewRef

