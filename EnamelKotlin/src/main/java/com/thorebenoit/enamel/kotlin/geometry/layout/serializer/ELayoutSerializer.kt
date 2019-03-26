package com.thorebenoit.enamel.kotlin.geometry.layout.serializer

import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType

interface ELayoutSerializer {
    val serializeClazz: ELayoutSerializer.(Class<out ELayout>) -> Unit
    val deserializeClazz: ELayoutDeserializer.() -> Class<ELayout>

    fun add(layouts: List<ELayout>)

    fun add(layout: ELayout)

    fun add(n: Number)

    fun add(str: String)

    fun add(p: EPointType)

    fun add(a: EAlignment)

    fun add(bool: Boolean)

    fun add(side: ERectEdge)

    fun add(padding: EOffset)

    fun add(size: ESizeType)

    fun toDeserializer(): ELayoutDeserializer

}