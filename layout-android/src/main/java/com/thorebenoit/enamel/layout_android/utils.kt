package com.thorebenoit.enamel.layout_android

import android.view.View
import com.thorebenoit.enamel.geometry.layout.refs.ELayoutRef
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