package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.kotlin.core.math.*
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.*
import com.thorebenoit.enamel.kotlin.physics.core.PhysicsBody
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet


class FlappyBirdApplet : KotlinPApplet() {
    private val gravity = EPoint(0, 0.5)

    inner class Player(val body: PhysicsBody, val radius: Number) {
        val collider: ERectType
            get() = ECircle(body.position, radius).outterRect()

        fun update() {
            body.update()
            if (collider.top < 0) {
                body.position.y = radius.f

            }


            if (collider.bottom < height) {
                body.addForce(gravity)
            } else {
                body.velocity.y = 0f
            }

        }

        fun jump() {
            body.addForce(gravity * -10)
        }
    }

    private val cutSizeRatio = 0.3f

    inner class Pipe(var x: Float = width.toFloat(), val cut: Float = random(cutSizeRatio, 1f - cutSizeRatio)) {
        val w = 50
        val cutSize get() = height * cutSizeRatio

        private val topRect = ERect()
        private val botRect = ERect()
        fun update() {
            x -= 5
            topRect.set(x, 0, w, (cut * height) - cutSize)
            botRect.set(x, topRect.bottom + cutSize, w, height)
            botRect.bottom = height.f

        }

        fun draw() {
            topRect.draw()
            botRect.draw()
        }

        fun collides(player: Player): Boolean = topRect.contains(player.collider) || botRect.contains(player.collider)

    }


    val player by lazy {
        Player(PhysicsBody(10f), 20)
            .apply { body.position.set(width / 3, height / 2f) }
    }
    val pipes by lazy { mutableListOf(Pipe()) }


    override fun draw() {
        background(255)
        fill(0)

        if (frameCount % 75 == 0) {
            pipes.add(Pipe())
        }
        pipes.removeIf { it.x < - it.w }

        if (keyPressed) {
            player.jump()
        }

        player.collider.draw()

        player.update()


        pipes.forEach {
            it.update()
            it.draw()
            if (it.collides(player)) {
                kotlin.io.println("collide !")
            }
        }
    }

}


fun main() {
    KotlinPApplet.createApplet<FlappyBirdApplet>(1500, 750)
}