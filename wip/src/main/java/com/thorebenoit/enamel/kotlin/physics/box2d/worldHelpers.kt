package com.thorebenoit.enamel.kotlin.physics.box2d

import com.thorebenoit.enamel.geometry.figures.ECircleMutable
import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.primitives.EPoint
import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.World

fun EPoint.toVec2() = Vec2(x, y)


fun World.addBox(rect: ERect, isStatic: Boolean = false): Body {
    val def = BodyDef()
    def.type = if (isStatic) BodyType.STATIC else BodyType.DYNAMIC

    val shape = PolygonShape()
    shape.setAsBox(
        rect.width / 2f,
        rect.height / 2f,
        rect.center().toVec2(),
        0f
    )

    val body = createBody(def)
    body.createFixture(shape, 1f)

    return body
}

fun World.addCircle(circle: ECircleMutable, isStatic: Boolean = false): Body {
    val def = BodyDef()
    def.type = if (isStatic) BodyType.STATIC else BodyType.DYNAMIC
    def.position = circle.center.toVec2()

    val shape = CircleShape()
    shape.radius = circle.radius

    val body = createBody(def)
    body.createFixture(shape, 1f)

    return body
}