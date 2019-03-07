package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType

interface ELayout {
    val childLayouts: List<ELayout>

    // TODO Refactor so these 2 functions don't allocate
    fun size(toFit: ESizeType): ESizeType
    fun arrange(frame: ERectType)
}

interface ELayoutAlongAxis : ELayout{
    val layoutAxis : ELayoutAxis
}

val ELayout.unconstrainedSize get() = size(ESizeType.greatestSize)

/* Custom (de)serializer
package com.thorebenoit.enamel.kotlin.geometry.layout

import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.primitives.point

abstract class ELayout(val childLayouts: List<ELayout>) {

    constructor(deserializer: ELayoutDeserializer) : this(deserializer.getLayouts())

//    abstract fun serialize(serializer: ELayoutSerializer)

    // TODO Refactor so these 2 functions don't allocate
    abstract fun size(toFit: ESizeType): ESizeType

    abstract fun arrange(frame: ERectType)
}

abstract class ELayoutAlongAxis(childLayouts: List<ELayout>) : ELayout(childLayouts) {
    abstract val layoutAxis: ELayoutAxis
}

val ELayout.unconstrainedSize get() = size(ESizeType.greatestSize)


class ELayoutSerializer() {

    val buffer: MutableList<Float> = mutableListOf()


    private fun put(vararg number: Number) {
        buffer.addAll(number.map { it.toFloat() })
    }

    private fun put(number: List<Number>) {
        buffer.addAll(number.map { it.toFloat() })
    }

    fun put(clazz: Class<ELayout>) {
        val name = clazz.name
        put(name.length)
        name.forEach {
            it.toShort()
        }
    }

    fun put(number: Number) {
        buffer += number.f
    }


    fun put(bool: Boolean) {
        buffer += bool.i.f
    }

    fun put(layoutAxis: ELayoutAxis) {
        buffer += layoutAxis.ordinal.f
    }

    fun put(rect: ERectType) {
        put(rect.origin)
        put(rect.size)
    }

    fun put(size: ESizeType) {
        put(size.width, size.height)
    }

    fun put(point: EPointType) {
        put(point.x, point.y)
    }

    fun put(offset: EOffset) {
        offset.apply {
            put(left, top, right, bottom)
        }
    }

    fun put(alignment: EAlignment) {
        alignment.namedPoint.apply {
            put(x, y)
        }
    }

    // Classes outside fo Enamel
    fun <T> put(obj: T, serializer: (T) -> List<Float>) {
        put(serializer(obj))
    }

}


class ELayoutDeserializer(val buffer: List<Number>) {

    private val iterator: Iterator<Number> = buffer.iterator()

    private inline fun next() = iterator.next()

    fun getLayoutAxis(): ELayoutAxis = ELayoutAxis.values()[next().i]

    fun getRect(): ERectType = ERectType(origin = getPoint(), size = getSize())

    fun getSize(): ESizeType = next() size next()

    fun getPoint(): EPointType = next() point next()

    fun getOffset(): EOffset = EOffset(next(), next(), next(), next())

    fun getAlignment(): EAlignment = EAlignment.fromNamedPoint(getPoint())

    // Classes outside fo Enamel
    fun <T> get(serializer: (Iterator<Number>) -> T): T = serializer(iterator)

    fun getLayouts(): List<ELayout> {

    }

}
 */