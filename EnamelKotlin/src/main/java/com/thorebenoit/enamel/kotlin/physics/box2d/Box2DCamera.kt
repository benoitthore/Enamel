package com.thorebenoit.enamel.kotlin.physics.box2d

import com.thorebenoit.enamel.kotlin.core.math.Scale
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import org.jbox2d.common.Vec2

/**
 * Taken from The Coding Train
 */
class Box2DCamera(
    val width: () -> Int,
    val height: () -> Int,

    var zoom: Int = 10,
    var yFlip: Boolean = true,
    var transX: Int = 0,
    var transY: Int = 0,
    val createPoint: (Number, Number) -> EPointType = { x, y -> x point y }
) {

    fun coordWorldToPixels(world: Vec2): Vec2 {
        return coordWorldToPixels(world.x, world.y)
    }

    fun coordWorldToPixelsEPointType(world: Vec2): EPointType {
        val v = coordWorldToPixels(world.x, world.y)
        return createPoint(v.x, v.y)
    }

    fun coordWorldToPixels(worldX: Float, worldY: Float): Vec2 {
        val pixelX = Scale.map(worldX, 0f, 1f, transX, transX + zoom)
        var pixelY = Scale.map(worldY, 0f, 1f, transY, transY + zoom)
        if (yFlip) pixelY = Scale.map(pixelY, 0f, height(), height(), 0f)
        return Vec2(pixelX, pixelY)
    }

    // convert Coordinate from pixel space to box2d world
    fun coordPixelsToWorld(screen: Vec2): Vec2 {
        return coordPixelsToWorld(screen.x, screen.y)
    }

    fun coordPixelsToWorld(screen: EPointType): Vec2 {
        return coordPixelsToWorld(screen.x, screen.y)
    }

    fun coordPixelsToWorld(pixelX: Float, pixelY: Float): Vec2 {
        val worldX = Scale.map(pixelX, transX, transX + zoom, 0f, 1f)
        var worldY = pixelY
        if (yFlip) worldY = Scale.map(pixelY, height(), 0f, 0f, height())
        worldY = Scale.map(worldY, transY, transY + zoom, 0f, 1f)
        return Vec2(worldX, worldY)
    }

    // Scale scalar quantity between worlds
    fun scalarPixelsToWorld(value: Float): Float {
        return value / zoom
    }

    fun scalarWorldToPixels(value: Float): Float {
        return value * zoom
    }

    // Scale vector between worlds
    fun vectorPixelsToWorld(v: Vec2): Vec2 {
        val u = Vec2(v.x / zoom, v.y / zoom)
        u.y *= if (yFlip) -1 else 1
        return u
    }

    fun vectorPixelsToWorld(v: EPointType): Vec2 {
        val u = Vec2(v.x.toFloat() / zoom, v.y.toFloat() / zoom)
        u.y *= if (yFlip) -1 else 1
        return u
    }

    fun vectorPixelsToWorld(x: Float, y: Float): Vec2 {
        val u = Vec2(x / zoom, y / zoom)
        u.y *= if (yFlip) -1 else 1
        return u
    }

    fun vectorWorldToPixels(v: Vec2): Vec2 {
        val u = Vec2(v.x * zoom, v.y * zoom)
        u.y *= if (yFlip) -1 else 1
        return u
    }

    fun vectorWorldToPixelsEPointType(v: Vec2): EPointType {
        val x = v.x * zoom
        var y = v.y * zoom
        y *= if (yFlip) -1 else 1
        return createPoint(x, y)
    }
}