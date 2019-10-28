package com.benoitthore.enamel.layout.android

import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.reflect.Constructor


internal fun mainThreadCoroutine(block: suspend CoroutineScope.() -> Unit) =
    GlobalScope.launch(context = Dispatchers.Main, block = block)


internal inline val <T : View> Class<T>.contextConstructor: Constructor<T>
    get() = constructors.filter {
        val params = it.parameterTypes
        return@filter params.size == 1 && params[0] == Context::class.java
    }.first() as Constructor<T>


internal inline fun View.doOnNextLayout(crossinline action: (view: View) -> Unit) {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            view: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            view.removeOnLayoutChangeListener(this)
            action(view)
        }
    })
}