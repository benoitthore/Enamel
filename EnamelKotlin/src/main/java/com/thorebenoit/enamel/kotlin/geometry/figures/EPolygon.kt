package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.animations.lerp
import com.thorebenoit.enamel.kotlin.core.math.Scale
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.lerp
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType

open class EPolygonType(open val points: List<EPointType>) {

}

class EPolygon(override val points: List<EPoint>) : EPolygonType(points) {

}

fun <P : EPoint> List<P>.fit(rect: ERectType): List<P> = fit(rect.size).apply { forEach { it.selfOffset(rect.origin) } }

fun <P : EPoint> List<P>.fit(size: ESizeImmutable): List<P> {

    if(isEmpty()){
        return this
    }

    val listSize = this.size

    val from = 0
    val to = size.width

    val top = 0
    val bottom = size.height


    val highestPoint = maxBy { it.y }!!.y
    val lowestPoint = minBy { it.y }!!.y

    forEachIndexed { index, p ->
        // TODO Replace the 0->1 maps by a division
        val xRatio = Scale.map(index, 0, listSize - 1, 0, 1)
        val xPosition = Scale.map(xRatio, 0f, 1f, from, to)

        val yRatio = (p.y - lowestPoint) / (highestPoint - lowestPoint)
        val yPosition = Scale.map(yRatio, 0, 1, top, bottom)
        p.set(xPosition, yPosition)
    }
    return this
}
