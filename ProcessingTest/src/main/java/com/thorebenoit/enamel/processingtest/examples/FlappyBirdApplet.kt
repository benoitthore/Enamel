package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.kotlin.ai.genetics.Genome
import com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.ToyNeuralNetwork
import com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.getGeneticsBasedNeuralNetwork
import com.thorebenoit.enamel.kotlin.core.math.*
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.*
import com.thorebenoit.enamel.kotlin.physics.core.PhysicsBody
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import org.deeplearning4j.nn.api.NeuralNetwork
import processing.core.PApplet

private fun ToyNeuralNetwork.doNetworkDecision(world: FlappyBirdWorld) {
    val firstPipe = world.pipes.first()


    val networkInput = listOf(
        world.player.body.position.y,
        firstPipe.x,
        firstPipe.botRect.top,
        firstPipe.topRect.bottom
    )
    val networkDecision = feedForward(
        networkInput
    )

    if (networkDecision[0] > networkDecision[1]) {
        world.player.jump()
    }


}

val population = ToyNeuralNetwork(4, 4, 2)
    .getGeneticsBasedNeuralNetwork(100, scale = 10) {
        val currentNeuralNetwork = it.individual
        val currentWorld = FlappyBirdWorld()
        var collided = false
        (0 until 1000).forEach {
            if (collided) {
                return@forEach
            }
            currentNeuralNetwork.doNetworkDecision(currentWorld)
            currentWorld.updatePhysics { collided = true }
        }

        val dist = currentWorld.player.body.position.x

        // The higher the distance, the better
        return@getGeneticsBasedNeuralNetwork dist
    }


class FlappyBirdApplet : KotlinPApplet() {


    val world: FlappyBirdWorld by lazy { FlappyBirdWorld(width.f, height.f) }

    var nn = ToyNeuralNetwork(4, 16, 2)

    init {

        coroutine {


            var generation = 0
            while (true) {


                population.evolve()

                generation++

                if (generation % 500 == 0) {
                    """
                        Generation : $generation
                        Best distance : ${population.best.second}
                    """.trimIndent().print()
                    nn = population.best.first.individual
//                    delay(10_000)
//                    world.reset()
                }
            }
        }

        onKeyPressed { world.player.jump() }
    }

    override fun setup() {
        super.setup()
        frame.isResizable = true
    }

    override fun draw() {
        background(255)
        fill(0)

        nn.doNetworkDecision(world)

        with(world) {

            updatePhysics {
                background(255f, 0f, 0f)
            }


            player.apply { show() }
            pipes.forEach { it.apply { show() } }
        }
    }


}

class FlappyBirdWorld(
    val _width: Float = 1500f,
    val _height: Float = 750f
) {

    val Number.Xpx get() = this.f / _width.f
    val Number.Ypx get() = this.f / _height.f


    val player by lazy {
        Player(50.Xpx, 50.Ypx)
            .apply { body.position.set(0.3, 0.2) }
    }

    val pipes by lazy { mutableListOf(Pipe()) }

    fun reset() {
        pipes.clear()
        pipes.add(Pipe())

        player.body.position.set(0.3, 0.2)
        player.body.velocity.reset()
        player.body.acceleration.reset()
        frameCount = 0
    }

    private var frameCount = 0
    private val cutSizeRatio = 0.3f
    private val gravity = EPoint(0, (0.5).Ypx)


    fun updatePhysics(onCollide: () -> Unit) {

        if (frameCount % 100 == 0) {
            pipes.add(Pipe())
        }
        pipes.removeIf {
            it.x < -it.w
        }



        player.update()
        pipes.forEach { it.update() }

        pipes.forEach {
            if (it.collides(player)) {
                onCollide()
            }
        }
        frameCount++
    }


    open inner class Player(val w: Number, val h: Number) {

        val body = PhysicsBody(maxVelocity = 10.Ypx)
        val collider: ERectType get() = ERect(body.position.x, body.position.y, w, h)

        fun update() {
            body.update()
            if (collider.top < 0) {
                body.position.y = 0f//collider.height.f / 2

                if (body.velocity.y < 0) {
                    body.velocity.y = 0f
                }
            }


            if (isJumping) {
                if (body.velocity.y > 0) {
                    body.velocity.y = 0f
                }
                body.addForce(gravity * -20)
            } else if (collider.bottom < 1f) {
                body.addForce(gravity)
            } else {
                body.velocity.y = 0f
            }
            isJumping = false
        }

        var isJumping = false
        fun jump() {
            isJumping = true
        }

        fun KotlinPApplet.show() {
            collider.scale(esize).draw()
        }
    }


    inner class Pipe(var x: Float = 1f, val cut: Float = random(cutSizeRatio, 1f - cutSizeRatio)) {
        val w = 40.Xpx
        val cutSize get() = cutSizeRatio

        val topRect = ERect()
        val botRect = ERect()
        fun update() {
            x -= 5.Xpx
            topRect.set(x, 0, w, cut - cutSize)
            botRect.set(x, topRect.bottom + cutSize, w, 1f)
            botRect.bottom = 1f

        }

        fun KotlinPApplet.show() {
            topRect.scale(esize).draw()
            botRect.scale(esize).draw()
        }

        fun collides(player: Player): Boolean =
            topRect.contains(player.collider)
                    || botRect.contains(player.collider)
                    || topRect.intersects(player.collider)
                    || botRect.intersects(player.collider)

    }

}


fun main() {
    KotlinPApplet.createApplet<FlappyBirdApplet>(1500, 750)
}