package com.benoitthore.enamel.layout_android

import android.content.Context
import android.content.res.Resources
import android.view.View
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.refs.ELayoutRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal fun mainThreadCoroutine(block: suspend CoroutineScope.() -> Unit) =
    GlobalScope.launch(context = Dispatchers.Main, block = block)


fun <T : View> T.withTag(tag: Any): T = apply {
    this.tag = tag
}

fun <T : View> ELayoutRef<T>.withTag(tag: Any): ELayoutRef<T> = apply {
    this.ref.viewRef.tag = tag
}


val <T : View> ELayoutRef<T>.view get() = ref.viewRef


fun Context.eViewGroup(block: EViewGroup.() -> ELayout): EViewGroup = EViewGroup(
    this
).apply {
    layout = block()
}

val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density).toInt()


