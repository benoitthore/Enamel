package com.benoitthore.enamel.android

import com.benoitthore.enamel.geometry.figures.ECircleMutable
import com.benoitthore.enamel.geometry.figures.ELineMutable
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.primitives.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.EPointMutable

/*
    val rectPool: Pool<ERectMutable>
    val pointPool: Pool<EPointMutable>
    val circlePool: Pool<ECircleMutable>
    val linePool: Pool<ELineMutable>
    val anglePool: Pool<EAngleMutable>

    allocate {
        rectPool = RectPool()
        pointPool = PointPool()
        circlePool = CirclePool()
        linePool = LinePool()
        anglePool = AnglePool()
    }
*/

/*
    val rectPool = RectPool()
    val pointPool = PointPool()
    val circlePool = CirclePool()
    val linePool = LinePool()
    val anglePool = AnglePool()
 */

//interface
class Pool<T : Any>(val size: Int, val init: (Int) -> T) {
    private val list = List(size, init)
    private var i = 0
        val next get() = list[i++ % size]
//    val next get() = init(0)

    operator fun invoke() = next

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
}

fun RectPool(size: Int = 50) = Pool(size) { ERectMutable() }
fun PointPool(size: Int = 50) = Pool(size) { EPointMutable() }
fun CirclePool(size: Int = 50) = Pool(size) { ECircleMutable() }
fun LinePool(size: Int = 50) = Pool(size) { ELineMutable() }
fun AnglePool(size: Int = 50) = Pool(size) { EAngleMutable() }

class GeometryPool(
    rectPoolSize: Int = 50,
    pointPoolSize: Int = 50,
    circlePoolSize: Int = 50,
    linePoolSize: Int = 50,
    anglePoolSize: Int = 50
) {
    constructor(size: Int) : this(size, size, size, size, size)

    val rectPool: Pool<ERectMutable> = RectPool(rectPoolSize)
    val pointPool: Pool<EPointMutable> = PointPool(pointPoolSize)
    val circlePool: Pool<ECircleMutable> = CirclePool(circlePoolSize)
    val linePool: Pool<ELineMutable> = LinePool(linePoolSize)
    val anglePool: Pool<EAngleMutable> = AnglePool(anglePoolSize)


}
