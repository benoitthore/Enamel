package com.thorebenoit.enamel.processingtest.box2d

/**
 * PBox2d
 * This is a simple little wrapper to help integrate JBox2d with Processing
 * It doesn't do much right now and, in some ways, limits the user
 * It's an open question as to whether this should really be a library
 * or a set of examples. Right now, it's a little bit of both
 * Daniel Shiffman <http:></http:>//www.shiffman.net>
 */


import org.jbox2d.callbacks.ContactListener
import org.jbox2d.common.IViewportTransform
import org.jbox2d.common.Transform
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.World
import org.jbox2d.dynamics.joints.Joint
import org.jbox2d.dynamics.joints.JointDef

import processing.core.PApplet
import processing.core.PVector

class Box2DProcessing() {

    lateinit var parent: PApplet
    var scaleFactor: Float = 10f// = 10.0f;

    // The Box2D world
    lateinit var world: World

    // Variables to keep track of translating between world and screen coordinates
    var transX: Float = 0.toFloat()// = 320.0f;
    var transY: Float = 0.toFloat()// = 240.0f;
    var yFlip: Float = 0.toFloat()// = -1.0f; //flip y coordinate


    lateinit var groundBody: Body

    internal var contactlistener: ContactListener? = null

    init {
        transX = (parent.width / 2).toFloat()
        transY = (parent.height / 2).toFloat()
        yFlip = -1f

    }

    //    public void listenForCollisions() {
    //        contactlistener = new ContactListener(parent);
    //        world.setContactListener(contactlistener);
    //    }

    // This is the all important physics "step" function
    // Says to move ahead one unit in time
    // Default
    fun step() {
        val timeStep = 1.0f / 60f
        this.step(timeStep, 10, 8)
        world.clearForces()
    }

    // Custom
    fun step(timeStep: Float, velocityIterations: Int, positionIterations: Int) {
        world.step(timeStep, velocityIterations, positionIterations)
    }

    fun setWarmStarting(b: Boolean) {
        world.isWarmStarting = b
    }

    fun setContinuousPhysics(b: Boolean) {
        world.isContinuousPhysics = b
    }

    // Create a default world with default gravity
    fun createWorld() {
        val gravity = Vec2(0.0f, -10.0f)
        createWorld(gravity)
        setWarmStarting(true)
        setContinuousPhysics(true)
    }

    //	public void createWorld(Vec2 gravity, boolean doSleep, boolean warmStarting, boolean continous) {
    //		world = new World(gravity, doSleep);
    //		setWarmStarting(warmStarting);
    //		setContinuousPhysics(continous);
    //	}

    @JvmOverloads
    fun createWorld(gravity: Vec2, warmStarting: Boolean = true, continous: Boolean = true) {
        world = World(gravity)
        setWarmStarting(warmStarting)
        setContinuousPhysics(continous)

        val bodyDef = BodyDef()
        groundBody = world.createBody(bodyDef)
    }


    // Set the gravity (this can change in real-time)
    fun setGravity(x: Float, y: Float) {
        world.gravity = Vec2(x, y)
    }

    // These functions are very important
    // Box2d has its own coordinate system and we have to move back and forth between them
    // convert from Box2d world to pixel space
    fun coordWorldToPixels(world: Vec2): Vec2 {
        return coordWorldToPixels(world.x, world.y)
    }

    fun coordWorldToPixelsPVector(world: Vec2): PVector {
        val v = coordWorldToPixels(world.x, world.y)
        return PVector(v.x, v.y)
    }

    fun coordWorldToPixels(worldX: Float, worldY: Float): Vec2 {
        val pixelX = PApplet.map(worldX, 0f, 1f, transX, transX + scaleFactor)
        var pixelY = PApplet.map(worldY, 0f, 1f, transY, transY + scaleFactor)
        if (yFlip == -1.0f) pixelY = PApplet.map(pixelY, 0f, parent.height.toFloat(), parent.height.toFloat(), 0f)
        return Vec2(pixelX, pixelY)
    }

    // convert Coordinate from pixel space to box2d world
    fun coordPixelsToWorld(screen: Vec2): Vec2 {
        return coordPixelsToWorld(screen.x, screen.y)
    }

    fun coordPixelsToWorld(screen: PVector): Vec2 {
        return coordPixelsToWorld(screen.x, screen.y)
    }

    fun coordPixelsToWorld(pixelX: Float, pixelY: Float): Vec2 {
        val worldX = PApplet.map(pixelX, transX, transX + scaleFactor, 0f, 1f)
        var worldY = pixelY
        if (yFlip == -1.0f) worldY = PApplet.map(pixelY, parent.height.toFloat(), 0f, 0f, parent.height.toFloat())
        worldY = PApplet.map(worldY, transY, transY + scaleFactor, 0f, 1f)
        return Vec2(worldX, worldY)
    }

    // Scale scalar quantity between worlds
    fun scalarPixelsToWorld(`val`: Float): Float {
        return `val` / scaleFactor
    }

    fun scalarWorldToPixels(`val`: Float): Float {
        return `val` * scaleFactor
    }

    // Scale vector between worlds
    fun vectorPixelsToWorld(v: Vec2): Vec2 {
        val u = Vec2(v.x / scaleFactor, v.y / scaleFactor)
        u.y *= yFlip
        return u
    }

    fun vectorPixelsToWorld(v: PVector): Vec2 {
        val u = Vec2(v.x / scaleFactor, v.y / scaleFactor)
        u.y *= yFlip
        return u
    }

    fun vectorPixelsToWorld(x: Float, y: Float): Vec2 {
        val u = Vec2(x / scaleFactor, y / scaleFactor)
        u.y *= yFlip
        return u
    }

    fun vectorWorldToPixels(v: Vec2): Vec2 {
        val u = Vec2(v.x * scaleFactor, v.y * scaleFactor)
        u.y *= yFlip
        return u
    }

    fun vectorWorldToPixelsPVector(v: Vec2): PVector {
        val u = PVector(v.x * scaleFactor, v.y * scaleFactor)
        u.y *= yFlip
        return u
    }

    // A common task we have to do a lot
    fun createBody(bd: BodyDef): Body {
        return world.createBody(bd)
    }

    // A common task we have to do a lot
    fun createJoint(jd: JointDef): Joint {
        return world.createJoint(jd)
    }

    // Another common task, find the position of a body
    // so that we can draw it
    fun getBodyPixelCoord(b: Body): Vec2 {
        val xf = b.transform//b.getXForm();
        //return coordWorldToPixels(xf.position);
        return coordWorldToPixels(xf.p)
    }

    fun getBodyPixelCoordPVector(b: Body): PVector {
        val xf = b.transform
        return coordWorldToPixelsPVector(xf.p)
    }

    fun destroyBody(b: Body) {
        world.destroyBody(b)
    }


}// Construct with a default scaleFactor of 10



