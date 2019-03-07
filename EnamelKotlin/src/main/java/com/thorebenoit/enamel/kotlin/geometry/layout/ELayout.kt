package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME


@JsonTypeInfo(use = NAME, include = PROPERTY)
interface ELayout {
    val childLayouts: List<ELayout>

    // TODO Refactor so these 2 functions don't allocate
    fun size(toFit: ESizeType): ESizeType

    fun arrange(frame: ERectType)
}

interface ELayoutAlongAxis : ELayout {
    val layoutAxis: ELayoutAxis
}

val ELayout.unconstrainedSize get() = size(ESizeType.greatestSize)


