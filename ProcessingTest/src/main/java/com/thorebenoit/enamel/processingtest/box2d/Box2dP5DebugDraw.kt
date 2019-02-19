package com.thorebenoit.enamel.processingtest.box2d

import org.jbox2d.callbacks.DebugDraw
import org.jbox2d.common.Color3f
import org.jbox2d.common.IViewportTransform
import org.jbox2d.common.Transform
import org.jbox2d.common.Vec2
import processing.core.PConstants
import processing.core.PGraphics

/**
 * Created by doekewartena on 6/30/15.
 */
class Box2dP5DebugDraw : DebugDraw(null) {

    var box2d: Box2DProcessing? = null
    var g: PGraphics? = null

    //    public Box2dP5DebugDraw(Box2DProcessing box2d) {
    //        super(null);
    //    }


    override fun drawPoint(argPoint: Vec2, argRadiusOnScreen: Float, argColor: Color3f) {
        println("drawPoint")
    }

    override fun drawSolidPolygon(vertices: Array<Vec2>, vertexCount: Int, color: Color3f) {

        g!!.noStroke()
        g!!.fill(color.x, color.y, color.z)

        g!!.beginShape()
        for (i in 0 until vertexCount) {
            val pixelVec = box2d!!.coordWorldToPixels(vertices[i])
            g!!.vertex(pixelVec.x, pixelVec.y)
        }
        g!!.endShape()

    }

    override fun drawCircle(center: Vec2, radius: Float, color: Color3f) {
        var center = center
        var radius = radius

        center = box2d!!.coordWorldToPixels(center)
        radius = box2d!!.scalarWorldToPixels(radius)

        g!!.ellipseMode(PConstants.CENTER)
        g!!.stroke(color.x, color.y, color.z)
        g!!.noFill()
        g!!.ellipse(center.x, center.y, radius * 2, radius * 2)
    }

    override fun drawSolidCircle(center: Vec2, radius: Float, axis: Vec2, color: Color3f) {
        var center = center
        var radius = radius

        center = box2d!!.coordWorldToPixels(center)
        radius = box2d!!.scalarWorldToPixels(radius)

        g!!.ellipseMode(PConstants.CENTER)
        g!!.fill(color.x, color.y, color.z)
        g!!.noStroke()
        g!!.ellipse(center.x, center.y, radius * 2, radius * 2)
    }

    override fun drawSegment(p1: Vec2, p2: Vec2, color: Color3f) {

        //        System.out.println();
        //        System.out.println("drawSegment");
        //        System.out.println(p1.x+" "+p1.y);
        //        System.out.println(p2.x+" "+p2.y);
        //        System.out.println(color.x+" "+color.y+" "+color.z);
        //        System.out.println();

        val v1 = box2d!!.coordWorldToPixels(p1)
        val v2 = box2d!!.coordWorldToPixels(p2)
        g!!.stroke(color.x, color.y, color.z)
        g!!.line(v1.x, v1.y, v2.x, v2.y)

    }

    // thanks to xnastudio
    override fun drawTransform(xf: Transform) {
        val k_axisScale = 0.4f
        val p1 = xf.p
        val p2 = Vec2()
        p2.setZero()
        p2.x = p1.x + k_axisScale * xf.q.c
        p2.y = p1.y + k_axisScale * xf.q.s
        val v1 = box2d!!.coordWorldToPixels(p1)
        var v2 = box2d!!.coordWorldToPixels(p2)
        g!!.stroke(1f, 0f, 0f)
        g!!.line(v1.x, v1.y, v2.x, v2.y)

        g!!.stroke(0f, 1f, 0f)
        p2.x = xf.p.x + -k_axisScale * xf.q.s
        p2.y = xf.p.y + k_axisScale * xf.q.c
        v2 = box2d!!.coordWorldToPixels(p2)
        g!!.line(v1.x, v1.y, v2.x, v2.y)
    }

    // thanks to xnastudio
    override fun drawString(x: Float, y: Float, s: String, color_: Color3f) {
        val v1 = box2d!!.coordWorldToPixels(x, y)
        g!!.fill(color_.x, color_.y, color_.z)
        g!!.text(s, v1.x, v1.y)
    }

}