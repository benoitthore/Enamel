package com.benoitthore.enamel.layout.android.extract

import com.benoitthore.enamel.core.LazyList
import com.benoitthore.enamel.geometry.e.E
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.geometry.primitives.*

class EPool<T : Any>(val size: Int, init: (Int) -> T) {
    private val list = LazyList(size, init)
    private var i = 0
    val next: T
        get() {
            if (i + 1 > size) {
                throw Exception("Pool overflow on ${list.firstOrNull()}")
            }
            return list[i++]
        }

    operator fun invoke() = next
    operator fun invoke(size: Int) = list(size)

    private val listBuffer = MutableList(size) { list[it] }

    fun list(size: Int): MutableList<T> {
        if (size > this.size) {
            throw Exception("Not enough in the pool")
        }
        listBuffer.clear()
        for (i in 0 until size) {
            listBuffer.add(list[i])
        }

        return listBuffer

    }

    fun reset() {
        i = 0
    }
}

fun RectPool(size: Int = 50) = EPool(size) { E.mrect() }
fun PointPool(size: Int = 50) =
    EPool(size) { E.mpoint() }

fun CirclePool(size: Int = 50) =
    EPool(size) { E.mcircle() }

fun LinePool(size: Int = 50) = EPool(size) { ELineMutable() }
fun AnglePool(size: Int = 50) =
    EPool(size) { E.mangle() }

class GeometryPool(
    val rect: EPool<ERectMutable> = RectPool(50),
    val point: EPool<EPointMutable> = PointPool(50),
    val circle: EPool<ECircleMutable> = CirclePool(50),
    val line: EPool<ELineMutable> = LinePool(50),
    val angle: EPool<EAngleMutable> = AnglePool(50)
) {
    constructor(size: Int) : this(size, size, size, size, size)

    constructor(
        rectPoolSize: Int = 50,
        pointPoolSize: Int = 50,
        circlePoolSize: Int = 50,
        linePoolSize: Int = 50,
        anglePoolSize: Int = 50
    ) : this(
        rect = RectPool(rectPoolSize),
        point = PointPool(pointPoolSize),
        circle = CirclePool(circlePoolSize),
        line = LinePool(linePoolSize),
        angle = AnglePool(anglePoolSize)
    )


    inline fun use(block: GeometryPool.() -> Unit) {
        reset()
        block()
        reset()
    }

    fun reset() {
        rect.reset()
        point.reset()
        circle.reset()
        line.reset()
        angle.reset()
    }

    val invokeMap: Map<Class<*>, () -> Any> = mapOf(
        ELine::class.java to { line() },
        ELineMutable::class.java to { line() },

        ERectMutable::class.java to { rect() },
        ERect::class.java to { rect() },

        EPointMutable::class.java to { point() },
        EPoint::class.java to { point() },

        ECircleMutable::class.java to { circle() },
        ECircle::class.java to { circle() },

        EAngleMutable::class.java to { angle() },
        EAngle::class.java to { angle() }


    )

    // Is this dodgy ?
    inline operator fun <reified T> invoke(
        onError: () -> T = { throw Exception("Cannot work with class ${T::class.java}") }
    ): T =
        invokeMap[T::class.java]?.let { it as T } ?: onError()

}
