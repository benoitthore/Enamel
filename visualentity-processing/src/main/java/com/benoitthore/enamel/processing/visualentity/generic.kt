package com.benoitthore.enamel.processing.visualentity

import com.benoitthore.visualentity.*
import java.lang.Exception

fun VisualEntity<*>.toProcessing(): ProcessingVisualEntity<*> =
    when (this) {
        is ECircleVisualEntity -> toProcessing()
        is ERectVisualEntity -> toProcessing()
        is ELineVisualEntity -> toProcessing()
        is EOvalVisualEntity -> toProcessing()
        else -> throw Exception("Invalid $this")
    }