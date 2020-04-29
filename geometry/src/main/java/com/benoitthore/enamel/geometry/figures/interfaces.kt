package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable

// TODO Add to Oval + Circle
interface ConvertsToPoint {
//  TODO Add pointAtAngle and toListOfPoint

}

// TODO Add to Rect, Oval, Circle
interface HasCenter {
    fun getCenter(target: EPointMutable = E.mPoint()): EPointMutable = target.set(centerX, centerY)
    val centerX: Float
    val centerY: Float
}

// TODO Add to RectMutable, OvalMutable
interface CanSetCenter : HasCenter {
    fun setCenter(x: Number, y: Number)
    fun setCenter(center: EPoint) = setCenter(center.x, center.y)
}

// TODO Add to Rect, Circle, Oval, Line
interface HasBounds {
    val left: Float
    val top: Float
    val right: Float
    val bottom: Float
    fun getBounds(target: ERectMutable = E.mRect()) =
        target.setSides(top = top, left = left, right = right, bottom = bottom)
}

interface CanSetBounds : HasBounds {
    fun setBounds(left: Number, top: Number, right: Number, bottom: Number)
    fun setBounds(rect: ERect) = with(rect) { setBounds(left, top, right, bottom) }
}