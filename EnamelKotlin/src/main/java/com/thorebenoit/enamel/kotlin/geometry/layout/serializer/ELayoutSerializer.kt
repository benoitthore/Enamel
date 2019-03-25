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
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import java.lang.Exception
import java.nio.charset.Charset
import java.util.*

fun createStringIDLayoutDataStore() =
    ELayoutDataStore(
        serializeClazz = { add(it.name) },
        deserializeClazz = { Class.forName(readString()) as Class<ELayout> }
    )


private val clazzes = listOf(
    EBoxLayout::class.java,
    EBoxLayout::class.java,
    EDivideLayout::class.java,
    EJustifiedLayout::class.java,
    ELayoutLeaf::class.java,
    EPaddingLayout::class.java,
    ESizingLayout::class.java,
    ESnuggingLayout::class.java,
    EStackLayout::class.java
)

fun createIntIDLayoutDataStore() =
    ELayoutDataStore(
        serializeClazz = { add(clazzes.indexOf(it)) },
        deserializeClazz = { clazzes[readNumber().toInt()] as Class<ELayout> }
    )

class ELayoutDataStore(
    val serializeClazz: ELayoutDataStore.(Class<out ELayout>) -> Unit,
    val deserializeClazz: ELayoutDataStore.() -> Class<ELayout>
) {

    private val serializers
//            : Map<Class<out ELayout>, ELayoutSerializer<out ELayout>>
            = mapOf(
        EBarLayout::class.java to EBarLayoutSerializer,
        EBoxLayout::class.java to EBoxLayoutSerializer,
        EDivideLayout::class.java to EDivideLayoutSerializer,
        EJustifiedLayout::class.java to EJustifiedLayoutSerializer,
        ELayoutLeaf::class.java to ELayoutLeafSerializer,
        EPaddingLayout::class.java to EPaddingLayoutSerializer,
        ESizingLayout::class.java to ESizingLayoutSerializer,
        ESnuggingLayout::class.java to ESnuggingLayoutSerializer,
        EStackLayout::class.java to EStackLayoutSerializer
    )

    fun add(layouts: List<ELayout>) {
        add(layouts.size)
        layouts.forEach { add(it) }
    }

    fun readLayouts(): List<ELayout> {
        val size = readNumber().toInt()
        return (0 until size).map { readLayout() }
    }

    fun add(layout: ELayout) {
        val layoutClazz = layout::class.java
        (serializers[layoutClazz])?.let { serializer ->
            serializeClazz(layoutClazz)

            serializer.serializeUnsafe(layout, this)
        } ?: kotlin.run {
            throw Exception("Don't know how to serialize layout: $layoutClazz")
        }
    }


    fun readLayout(): ELayout {
        val layoutClazz = deserializeClazz()
        return serializers[layoutClazz]?.let { serializer ->
            serializer.deserialize(this)
        } ?: kotlin.run {
            throw Exception("Don't know how to deserialize layout: $layoutClazz")
        }
    }


    val data = LinkedList<Number>()

    fun add(n: Number) {
        data.add(n)
    }

    fun readNumber() = data.pollFirst().print

    fun add(str: String) {
        add(str.length)
        val arr = str.toByteArray(Charset.defaultCharset())
        arr.forEach { add(it) }
    }

    fun readString(): String {
        val length = readNumber().i
        val bytes = (0 until length).map { readNumber().toByte() }.toByteArray()
        return bytes.toString(Charset.defaultCharset())
    }

    /// Point
    fun add(p: EPointType) {
        data.add(p.x)
        data.add(p.y)
    }

    fun readPoint() = EPoint(readNumber(), readNumber())

    /// Alignment
    fun add(a: EAlignment) {
        data.add(a.ordinal)
    }

    fun readAlignment(): EAlignment = EAlignment.values()[readNumber().i]


    /// Bool
    fun add(bool: Boolean) {
        data.add(bool.i)
    }

    fun readBool() = readNumber().bool


    /// RectEdge
    fun add(side: ERectEdge) {
        data.add(side.ordinal)
    }

    fun readRectEdge() = ERectEdge.values()[readNumber().i]

    /// Offset
    fun add(padding: EOffset) {
        data.add(padding.left)
        data.add(padding.top)
        data.add(padding.right)
        data.add(padding.bottom)
    }

    fun readOffset() = EOffset(
        left = readNumber(),
        top = readNumber(),
        right = readNumber(),
        bottom = readNumber()
    )


    /// Size
    fun add(size: ESizeType) {
        data.add(size.width)
        data.add(size.height)
    }

    fun readSize() = ESize(data.peekLast(), data.peekLast())

}


fun main() {
    val layout =
        2.of { ELayoutLeaf(123) }
        .mapIndexed { i, layout ->
            layout.sizedSquare((i + 1) * 100)
        }
        .stacked(EAlignment.topLeft, spacing = 321)
//        .snugged()
//        .arranged(EAlignment.topLeft)
//        .padded(20)


    val store = createIntIDLayoutDataStore()
//    store.add(
//        ELayoutLeaf(12345).padded(10).arranged(EAlignment.middle)
//        ELayoutLeaf(12345).padded(10)
//    )

    store.add(layout)

    store.data.joinToString().print
    store.readLayout().print

}
