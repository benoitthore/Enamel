package com.thorebenoit.enamel.processingtest.examples.steering

import com.thorebenoit.enamel.kotlin.core.color.randomColor
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.EPolygon
import com.thorebenoit.enamel.kotlin.geometry.figures.toPolygon
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.kotlin.physics.core.PhysicsBody
import com.thorebenoit.enamel.kotlin.physics.steering.SteeringController

data class SteeringVehicle(
    val position: EPoint = 0 point 0,
    val color: Int = randomColor(),
    val radius: Int = 5,
    val torque: Float = 0.1f,
    val maxVelocity: Float = 100f / 60
) {

    val shape: List<EPolygon> = kotlin.run {
//        val rect1 = ERectCenter(width = 0.3, height = 1).scaleAnchor(radius, NamedPoint.center)
//        val circle = ECircle(center = rect1.width + radius * 0.3 point 0, radius = radius * 0.3)

        listOf(
//            rect1.toPointList().toPolygon(),
            ECircle(radius = radius).toListOfPoint(20).toPolygon()
        )
    }

    val body = PhysicsBody(maxVelocity, position)
    val controller: SteeringController = SteeringController(
        torque = torque,
        physicsBody = body
    )

}