package com.benoitthore.enamel.layout.android.extract

import com.benoitthore.enamel.geometry.figures.ECircleMutable
import com.benoitthore.enamel.geometry.figures.ELineMutable
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.primitives.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.EPointMutable

class Pool<T : Any>(val size: Int, init: (Int) -> T) {
    private val list = List(size, init)
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

fun RectPool(size: Int = 50) = Pool(size) { ERectMutable() }
fun PointPool(size: Int = 50) =
    Pool(size) { EPointMutable() }

fun CirclePool(size: Int = 50) =
    Pool(size) { ECircleMutable() }

fun LinePool(size: Int = 50) = Pool(size) { ELineMutable() }
fun AnglePool(size: Int = 50) =
    Pool(size) { EAngleMutable() }

class GeometryPool(
    val rectPool: Pool<ERectMutable> = RectPool(50),
    val pointPool: Pool<EPointMutable> = PointPool(50),
    val circlePool: Pool<ECircleMutable> = CirclePool(50),
    val linePool: Pool<ELineMutable> = LinePool(50),
    val anglePool: Pool<EAngleMutable> = AnglePool(50)
) {
    constructor(size: Int) : this(size, size, size, size, size)

    constructor(
        rectPoolSize: Int = 50,
        pointPoolSize: Int = 50,
        circlePoolSize: Int = 50,
        linePoolSize: Int = 50,
        anglePoolSize: Int = 50
    ) : this(
        rectPool = RectPool(rectPoolSize),
        pointPool = PointPool(pointPoolSize),
        circlePool = CirclePool(circlePoolSize),
        linePool = LinePool(linePoolSize),
        anglePool = AnglePool(anglePoolSize)
    )


    inline fun use(block: GeometryPool.() -> Unit) {
        reset()
        block()
        reset()
    }

    fun reset() {
        rectPool.reset()
        pointPool.reset()
        circlePool.reset()
        linePool.reset()
        anglePool.reset()
    }


}
