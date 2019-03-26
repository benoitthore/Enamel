package com.thorebenoit.enamel.kotlin.geometry.layout.serializer.digital

import com.thorebenoit.enamel.kotlin.core.math.bool
import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import java.nio.charset.Charset
import java.util.*

class ELayoutDeserializerDigital(
    data: List<Number>,
    override val deserializeClazz: ELayoutDeserializer.() -> Class<ELayout> // Reads class ID from the store
) : ELayoutDeserializer {

    companion object {
        fun createIntIDDesrializer(data: List<Number>) =
            ELayoutDeserializerDigital(data) { ELayoutSerializerDigital.clazzes[readNumber().toInt()] as Class<ELayout> }
    }

    val data = LinkedList(data)


    override fun readLayouts(): List<ELayout> {
        val size = readNumber().toInt()
        return (0 until size).map { readLayout() }
    }

    override fun readLayout(): ELayout {
        val layoutClazz = deserializeClazz()
        val instance = layoutClazz.newInstance()
        instance.deserialize(this)
        return instance
    }


    override fun readNumber() = data.pollFirst()


    override fun readString(): String {
        val length = readNumber().i
        val bytes = (0 until length).map { readNumber().toByte() }.toByteArray()
        return bytes.toString(Charset.defaultCharset())
    }


    override fun readPoint() = EPoint(readNumber(), readNumber())


    override fun readAlignment(): EAlignment = EAlignment.values()[readNumber().i]


    override fun readBool() = readNumber().bool


    override fun readRectEdge() = ERectEdge.values()[readNumber().i]


    override fun readOffset() = EOffset(
        left = readNumber(),
        top = readNumber(),
        right = readNumber(),
        bottom = readNumber()
    )


    override fun readSize() = ESize(readNumber(),readNumber())

}
