package com.thorebenoit.enamel.processingtest.box2d

import com.thorebenoit.enamel.core.print
import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ERectCenter
import com.thorebenoit.enamel.geometry.figures.ESizeMutable
import com.thorebenoit.enamel.geometry.figures.size
import com.thorebenoit.enamel.geometry.primitives.point
import com.thorebenoit.enamel.kotlin.physics.box2d.addBox
import com.thorebenoit.enamel.kotlin.physics.box2d.toVec2
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.PlaygroundApplet
import org.jbox2d.callbacks.DebugDraw
import org.jbox2d.dynamics.World

fun box2dTest(){
    PlaygroundApplet.start(800 size 800) {
        val world = World((0 point -9.91).toVec2())
        world.isWarmStarting = true
        world.isContinuousPhysics = true

        val debugDraw = Box2dP5DebugDraw(this)

        debugDraw.flags = DebugDraw.e_shapeBit + DebugDraw.e_jointBit

        world.setDebugDraw(debugDraw)

        onSetup {
            world.addBox(ERect(size = ESizeMutable(width, 10)).print, isStatic = true)
        }

        onMouseClicked {
            val rect = ERectCenter(mousePosition, 50, 50)
            world.addBox(rect)
        }



        onDraw {
            background(255)
            world.step(1f, 8, 2)
            world.drawDebugData()

        }
    }
}