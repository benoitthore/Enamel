package com.thorebenoit.enamel.kotlin.geometry.figures

import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType

open class EPolygonType(open val points: List<EPointType>) {

}

class EPolygon(override val points: List<EPoint>) : EPolygonType(points) {

}
