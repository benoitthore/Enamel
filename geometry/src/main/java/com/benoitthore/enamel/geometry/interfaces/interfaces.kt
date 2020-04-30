package com.benoitthore.enamel.geometry.interfaces

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable

// TODO Add to Oval + Circle
interface ConvertsToPoint {
//  TODO Add pointAtAngle and toListOfPoint

}

// TODO Add to Rect, Oval, Circle
interface HasCenter {
    fun getCenter(target: EPointMutable = E.PointMutable()): EPointMutable =
        target.set(centerX, centerY)

    val centerX: Float
    val centerY: Float
}

// TODO Add to RectMutable, OvalMutable
interface CanSetCenter : HasCenter {
    fun setCenter(x: Number, y: Number)
    fun setCenter(center: EPoint) = setCenter(center.x, center.y)
}

// TODO Add to Rect, Circle, Oval, Line
