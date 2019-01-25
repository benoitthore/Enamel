package com.thorebenoit.enamel.android.dsl

import android.content.Context
import android.view.View
import android.view.ViewGroup
import java.lang.reflect.Constructor


inline fun <reified T : View> ViewGroup.customView(init: T.() -> Unit = {}): T =
    context.createView(init).apply {
        addView(this)
        init()
    }

inline fun <reified T : View> Context.createView(init: T.() -> Unit = {}): T {
    return T::class.java.contextContructor.newInstance(this).apply(init)
}

inline val <T : View> Class<T>.contextContructor: Constructor<T>
    get() = constructors.filter {
        val params = it.parameterTypes
        return@filter params.size == 1 && params[0] == Context::class.java
    }.first() as Constructor<T>


