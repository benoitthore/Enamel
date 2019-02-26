package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType

interface ELayout {
    val childLayouts: List<ELayout>
    fun size(toFit: ESizeType): ESizeType
    fun arrange(frame: ERectType)
}

val ELayout.unconstrainedSize get() = size(ESizeType.greatestSize)

fun ELayout.toRectList(frame: ERectType): List<ERectType> {
    TODO()
}