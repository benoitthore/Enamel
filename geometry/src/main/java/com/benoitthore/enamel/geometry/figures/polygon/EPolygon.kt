package com.benoitthore.enamel.geometry.figures.polygon

//import com.benoitthore.enamel.core.math.Scale
//import com.benoitthore.enamel.geometry.primitives.EPointMutable
//import com.benoitthore.enamel.geometry.primitives.EPoint
//
//open class EPolygonType(open val points: List<EPoint>)
//
//class EPolygon(override val points: List<EPointMutable>) : EPolygonType(points)
//
//fun <P : EPointMutable> List<P>.fit(rect: ERect): List<P> = fit(rect.size).apply { forEach { it.selfOffset(rect.origin) } }
//
//fun <P : EPointMutable> List<P>.fit(size: ESize): List<P> {
//
//    if (isEmpty()) {
//        return this
//    }
//
//    val listSize = this.size
//
//    val from = 0
//    val to = size.width
//
//    val top = 0
//    val bottom = size.height
//
//
//    val highestPoint = maxBy { it.y }!!.y
//    val lowestPoint = minBy { it.y }!!.y
//
//    forEachIndexed { index, p ->
//        // TODO Replace the 0->1 maps by a division
//        val xRatio = Scale.map(index, 0, listSize - 1, 0, 1)
//        val xPosition = Scale.map(xRatio, 0f, 1f, from, to)
//
//        val yRatio = (p.y - lowestPoint) / (highestPoint - lowestPoint)
//        val yPosition = Scale.map(yRatio, 0, 1, top, bottom)
//        p.set(xPosition, yPosition)
//    }
//    return this
//}
//
//fun List<EPoint>.toPolygon() = EPolygonType(this)
//fun List<EPointMutable>.toPolygon() = EPolygon(this)
