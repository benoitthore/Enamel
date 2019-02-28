package com.thorebenoit.enamel.kotlin.geometry.layout.dsl

import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ESizingLayout

/*
    class Size(val size: ESizeType) : ELayoutSpace()
    class Width(val width: Number) : ELayoutSpace()
    class Height(val height: Number) : ELayoutSpace()
    class Scale(val horizontal: Number?, val vertical: Number?) : ELayoutSpace()
    class AspectFitting(val size: ESizeType) : ELayoutSpace()
    class AspectFilling(val size: ESizeType) : ELayoutSpace()
    class Func(val f: (ESizeType) -> ESizeType) : ELayoutSpace()
 */

fun ELayout.withSize(size: ESizeType) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Size(size))
fun ELayout.withSize(width: Number, height: Number) = withSize(width size height)

fun ELayout.withWidth(width: Number) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Width(width))
fun ELayout.withHeight(height: Number) = ESizingLayout(this, ESizingLayout.ELayoutSpace.Width(height))

fun ELayout.fitting(size: ESizeType) = ESizingLayout(this, ESizingLayout.ELayoutSpace.AspectFitting(size))
fun ELayout.fitting(width: Number, height: Number) = fitting(width size height)

fun ELayout.filling(size: ESizeType) = ESizingLayout(this, ESizingLayout.ELayoutSpace.AspectFilling(size))
fun ELayout.filling(width: Number, height: Number) = filling(width size height)