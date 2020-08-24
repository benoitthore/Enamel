package com.benoitthore.enamel.visualentity.android

import com.benoitthore.visualentity.*
import java.lang.Exception

fun VisualEntity<*>.toAndroid(): AndroidVisualEntity<*> =
    when (this) {
        is ECircleVisualEntity -> toAndroid()
        is ERectVisualEntity -> toAndroid()
        is ELineVisualEntity -> toAndroid()
        is EOvalVisualEntity -> toAndroid()
        else -> throw Exception("Invalid $this")
    }