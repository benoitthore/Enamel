package com.thorebenoit.enamel.kotlin.geometry.layout.serializer

import com.thorebenoit.enamel.kotlin.core.math.bool
import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType

interface ELayoutDeserializer {

    val deserializeClazz: ELayoutDeserializer.() -> Class<ELayout>

    val newInstance: (Class<ELayout>) -> ELayout

    fun readLayouts(): List<ELayout>

    fun readLayout(): ELayout

    fun readNumber(): Number

    fun readString(): String

    fun readPoint(): EPoint

    fun readAlignment(): EAlignment

    fun readBool(): Boolean

    fun readRectEdge(): ERectEdge

    fun readOffset(): EOffset

    fun readSize(): ESize
}