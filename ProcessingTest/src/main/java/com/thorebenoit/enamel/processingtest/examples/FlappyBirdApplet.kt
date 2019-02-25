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
import com.thorebenoit.enamel.kotlin.threading.CoroutineLock
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import org.deeplearning4j.nn.api.NeuralNetwork
import processing.core.PApplet

private fun ToyNeuralNetwork.doNetworkDecision(world: FlappyBirdWorld) {
    val firstPipe = world.pipes.first { it.x > world.player.body.position.x }


    val networkInput = listOf(
        world.player.body.position.y,
        world.player.body.velocity.y,
        firstPipe.x,
        firstPipe.botRect.top,
        firstPipe.topRect.bottom
    )
    val networkDecision = feedForward(networkInput)

    if (networkDecision[0] > networkDecision[1]) {
        world.player.jump()
    }


}

val BEST_FINTESS = 10_000f

val population = ToyNeuralNetwork(5, 8, 2)
    .getGeneticsBasedNeuralNetwork(100, scale = 1) {
        val currentNeuralNetwork = it.individual
        val currentWorld = FlappyBirdWorld()

        var score = 0f
        var collided = false
        (0 until BEST_FINTESS.toInt()).forEach {
            if (collided) {
                return@getGeneticsBasedNeuralNetwork score
            }
            currentNeuralNetwork.doNetworkDecision(currentWorld)
            currentWorld.updatePhysics { collided = true }
            if (currentWorld.player.body.position.y > 1f) {
                return@getGeneticsBasedNeuralNetwork 0f
            }
            score++
        }


        // The higher the distance, the better
        return@getGeneticsBasedNeuralNetwork score
    }


class FlappyBirdApplet : KotlinPApplet() {


    val world: FlappyBirdWorld by lazy { FlappyBirdWorld(width.f, height.f) }

    var nn = population.evolve().let { population.best.first.individual }

    val coroutineLock = CoroutineLock()

    init {

        coroutine {
            coroutineLock.wait()


            var generation = 0
            var lastBestDistance = 0f
            while (true) {


                population.evolve()

                generation++


                if (generation % 10 == 0) {
                    val currentBestDistance = population.best.second
                    """
                        Generation : $generation
                        Best distance : ${population.best.second}
                    """.trimIndent().print()

                    if (!lastBestDistance.nearlyEquals(currentBestDistance)) {

                        lastBestDistance = currentBestDistance

                        nn = population.best.first.individual
                        world.reset()
                    }

                    if (currentBestDistance > BEST_FINTESS - 10f) {
                        return@coroutine
                    }

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
        coroutineLock.unlock()
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
    }
    val pipes by lazy { mutableListOf(Pipe()) }


    init {
        reset()
    }


    fun reset() {
        pipes.clear()
        pipes.add(Pipe())

        player.body.position.set(0.1, 0.2)
        player.body.velocity.reset()
        player.body.acceleration.reset()
        frameCount = 0
    }

    private var frameCount = 0
    private val cutSizeRatio = 0.2f
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