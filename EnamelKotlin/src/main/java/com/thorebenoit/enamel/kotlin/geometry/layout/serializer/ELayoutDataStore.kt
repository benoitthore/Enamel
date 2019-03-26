package com.thorebenoit.enamel.kotlin.geometry.layout.serializer

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.thorebenoit.enamel.kotlin.core.data.ignoreUnknownObjectMapper
import com.thorebenoit.enamel.kotlin.core.math.bool
import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.core.of
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.core.time.ETimer
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.*
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.*
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.digital.ELayoutDeserializerDigital
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.digital.ELayoutSerializerDigital
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import java.lang.Exception
import java.nio.charset.Charset
import java.util.*


fun main() {

    val layout =
        1.of { ELayoutLeaf(123) }
            .mapIndexed { i, layout ->
                layout.sizedSquare((i + 1) * 100)
            }
            .stacked(EAlignment.topLeft, spacing = 321)
            .snugged()
            .arranged(EAlignment.topLeft)
            .padded(20)


    val store = ELayoutSerializerDigital.createIntIDSerializer()

    store.add(layout)

    store.data.joinToString().print
    store.toDeserializer().readLayout().print

}
