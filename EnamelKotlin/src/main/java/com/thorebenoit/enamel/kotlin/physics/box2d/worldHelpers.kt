package com.thorebenoit.enamel.kotlin.physics.box2d

import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.IViewportTransform
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.World

fun EPointType.toVec2() = Vec2(x, y)


fun World.addBox(rect: ERectType, isStatic: Boolean = false): Body {
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

fun World.addCircle(circle: ECircle, isStatic: Boolean = false): Body {
    val def = BodyDef()
    def.type = if (isStatic) BodyType.STATIC else BodyType.DYNAMIC
    def.position = circle.center.toVec2()

    val shape = CircleShape()
    shape.radius = circle.radius

    val body = createBody(def)
    body.createFixture(shape, 1f)

    return body
}