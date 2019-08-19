package com.benoitthore.enamel.layout_android

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.refs.ELayoutRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.reflect.Constructor

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

internal operator fun ViewGroup.iterator() = object : MutableIterator<View> {
    private var index = 0
    override fun hasNext() = index < childCount
    override fun next() = getChildAt(index++) ?: throw IndexOutOfBoundsException()
    override fun remove() = removeViewAt(--index)
}

/** Returns a [Sequence] over the child views in this view group. */
internal val ViewGroup.children: Sequence<View>
    get() = object : Sequence<View> {
        override fun iterator() = this@children.iterator()
    }