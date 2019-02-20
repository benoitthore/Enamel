package com.thorebenoit.enamel.processingtest.box2d

import org.jbox2d.callbacks.DebugDraw
import org.jbox2d.common.*
import processing.core.PConstants
import processing.core.PGraphics

class Box2dP5DebugDraw(
    var g: PGraphics,
    val transform: IViewportTransform = OBBViewportTransform()
) : DebugDraw(transform) {

//    init {
//        // TODO Make this work by copying the OBBVViewportTransform class, check "private final Mat22 yFlipMatInv = yFlipMat.invert();"
//        transform.isYFlip = true
//    }


    override fun drawPoint(argPoint: Vec2, argRadiusOnScreen: Float, argColor: Color3f) {
        drawCircle(argPoint, argRadiusOnScreen, argColor)
    }

    override fun drawSolidPolygon(vertices: Array<Vec2>, vertexCount: Int, color: Color3f) {

        g.noStroke()
        g.fill(color.x, color.y, color.z)

        g.beginShape()
        val pixelVec = Vec2()
        for (i in 0 until vertexCount) {
            transform.getWorldToScreen(vertices[i], pixelVec)
            g.vertex(pixelVec.x, pixelVec.y)
        }
        g.endShape()

    }

    override fun drawCircle(center: Vec2, radius: Float, color: Color3f) {
        var center = center
        var radius = radius

        // CHECK
        transform.getWorldToScreen(center, center)
        // CHECK
//        radius = box2d.scalarWorldToPixels(radius)

        g.ellipseMode(PConstants.CENTER)
        g.stroke(color.x, color.y, color.z)
        g.noFill()
        g.ellipse(center.x, center.y, radius * 2, radius * 2)
    }

    override fun drawSolidCircle(center: Vec2, radius: Float, axis: Vec2, color: Color3f) {
        var center = center
        var radius = radius

        // CHECK
        transform.getWorldToScreen(center, center)
        // CHECK
//        radius = box2d.scalarWorldToPixels(radius)

        g.ellipseMode(PConstants.CENTER)
        g.fill(color.x, color.y, color.z)
        g.noStroke()
        g.ellipse(center.x, center.y, radius * 2, radius * 2)
    }

    override fun drawSegment(p1: Vec2, p2: Vec2, color: Color3f) {
        val v1 = Vec2()
        val v2 = Vec2()
        transform.getWorldToScreen(p1, v1)
        transform.getWorldToScreen(p2, v2)
        g.stroke(color.x, color.y, color.z)
        g.line(v1.x, v1.y, v2.x, v2.y)

    }

    // thanks to xnastudio
    override fun drawTransform(xf: Transform) {
        val v1 = Vec2()
        val v2 = Vec2()

        val k_axisScale = 0.4f
        val p1 = xf.p
        val p2 = Vec2()
        p2.setZero()
        p2.x = p1.x + k_axisScale * xf.q.c
        p2.y = p1.y + k_axisScale * xf.q.s
        transform.getWorldToScreen(p1, v1)
        transform.getWorldToScreen(p2, v2)
        g.stroke(1f, 0f, 0f)
        g.line(v1.x, v1.y, v2.x, v2.y)

        g.stroke(0f, 1f, 0f)
        p2.x = xf.p.x + -k_axisScale * xf.q.s
        p2.y = xf.p.y + k_axisScale * xf.q.c
        transform.getWorldToScreen(p2, v2)
        g.line(v1.x, v1.y, v2.x, v2.y)
    }

    // thanks to xnastudio
    override fun drawString(x: Float, y: Float, s: String, color_: Color3f) {
        val v1 = Vec2()
        transform.getWorldToScreen(Vec2(x, y), v1)
        g.fill(color_.x, color_.y, color_.z)
        g.text(s, v1.x, v1.y)
    }

}