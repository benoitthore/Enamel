package com.thorebenoit.enamel.kotlin.geometry.layout.serializer.digital

import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.*
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutDeserializer
import com.thorebenoit.enamel.kotlin.geometry.layout.serializer.ELayoutSerializer
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import java.nio.charset.Charset
import java.util.*

class ELayoutSerializerDigital(
    override val serializeClazz: ELayoutSerializer.(Class<out ELayout>) -> Unit,
    override val deserializeClazz: ELayoutDeserializer.() -> Class<ELayout>
) : ELayoutSerializer {

    companion object {

        internal val clazzes = listOf(
            EBoxLayout::class.java, // 0
            EBoxLayout::class.java, // 1
            EDivideLayout::class.java, // 2
            EJustifiedLayout::class.java, // 3
            ELayoutLeaf::class.java, // 4
            EPaddingLayout::class.java, // 5
            ESizingLayout::class.java, // 6
            ESnuggingLayout::class.java, // 7
            EStackLayout::class.java // 8
        )

        fun createIntIDSerializer() = ELayoutSerializerDigital(
            { add(clazzes.indexOf(it)) },
            { clazzes[readNumber().toInt()] as Class<ELayout> }
        )

    }

    val data = LinkedList<Number>()

    override fun toDeserializer(): ELayoutDeserializer = ELayoutDeserializerDigital(data, deserializeClazz)

    override fun add(layouts: List<ELayout>) {
        add(layouts.size)
        layouts.forEach { add(it) }
    }

    override fun add(layout: ELayout) {
        val layoutClazz = layout::class.java
        serializeClazz(layoutClazz)
        layout.serialize(this)
    }

    override fun add(n: Number) {
        data.add(n)
    }

    override fun add(str: String) {
        add(str.length)
        val arr = str.toByteArray(Charset.defaultCharset())
        arr.forEach { add(it) }
    }

    override fun add(p: EPointType) {
        data.add(p.x)
        data.add(p.y)
    }


    override fun add(a: EAlignment) {
        data.add(a.ordinal)
    }


    override fun add(bool: Boolean) {
        data.add(bool.i)
    }

    override fun add(side: ERectEdge) {
        data.add(side.ordinal)
    }

    override fun add(padding: EOffset) {
        data.add(padding.left)
        data.add(padding.top)
        data.add(padding.right)
        data.add(padding.bottom)
    }

    override fun add(size: ESizeType) {
        data.add(size.width)
        data.add(size.height)
    }

}
