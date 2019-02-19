package com.thorebenoit.enamel.processingtest.box2d

import org.jbox2d.common.IViewportTransform
import org.jbox2d.common.Vec2

class MyIViewportTransform(val camera: Box2DProcessing) : IViewportTransform {
    override fun setCamera(x: Float, y: Float, scale: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCenter(): Vec2 {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getScreenVectorToWorld(argScreen: Vec2?, argWorld: Vec2?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCenter(argPos: Vec2?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCenter(x: Float, y: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setYFlip(yFlip: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isYFlip(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getScreenToWorld(argScreen: Vec2?, argWorld: Vec2?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setExtents(argExtents: Vec2?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setExtents(argHalfWidth: Float, argHalfHeight: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWorldToScreen(argWorld: Vec2?, argScreen: Vec2?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWorldVectorToScreen(argWorld: Vec2?, argScreen: Vec2?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getExtents(): Vec2 {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}