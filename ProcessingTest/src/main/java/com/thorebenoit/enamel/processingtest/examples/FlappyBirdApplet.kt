package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.core.fromJson
import com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.ToyNeuralNetwork
import com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.getGeneticsBasedNeuralNetwork
import com.thorebenoit.enamel.core.math.f
import com.thorebenoit.enamel.core.math.nearlyEquals
import com.thorebenoit.enamel.core.math.random
import com.thorebenoit.enamel.core.print
import com.thorebenoit.enamel.core.threading.CoroutineLock
import com.thorebenoit.enamel.geometry.alignement.EAlignment
import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ERectType
import com.thorebenoit.enamel.geometry.figures.ESizeType
import com.thorebenoit.enamel.geometry.primitives.EPointMutable
import com.thorebenoit.enamel.geometry.primitives.times
import com.thorebenoit.enamel.kotlin.physics.core.PhysicsBody
import com.thorebenoit.enamel.core.threading.coroutine
import com.thorebenoit.enamel.core.toJson
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import java.io.File
import java.nio.charset.Charset

private fun ToyNeuralNetwork.doNetworkDecision(world: FlappyBirdWorld) {
    val pipesAfterPlayer = world.pipes.filter { it.x + it.w > world.player.body.position.x }

    val firstPipe = pipesAfterPlayer.firstOrNull() ?: return
    val secondPipe = pipesAfterPlayer.getOrNull(1)


    val networkInput = listOf(
        world.player.body.position.y,
        world.player.body.velocity.y,
        firstPipe.x,
        firstPipe.botRect.top,
        firstPipe.topRect.bottom,
        secondPipe?.x ?: -1f,
        secondPipe?.botRect?.top ?: -1f,
        secondPipe?.topRect?.bottom ?: -1f
    )
    val networkDecision = feedForward(networkInput)

    if (networkDecision[0] > networkDecision[1]) {
        world.player.jump()
    }


}

val BEST_FINTESS = 1_000_000f

val population = ToyNeuralNetwork(8, 8, 2)
    .getGeneticsBasedNeuralNetwork(100, mutationRate = 0.05f) {
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
        return@getGeneticsBasedNeuralNetwork score//.pow(2)
    }


class FlappyBirdApplet : KotlinPApplet() {


    val world: FlappyBirdWorld by lazy { FlappyBirdWorld(width.f, height.f) }

    var nn = population.evolve().let { population.best.first.individual }

    val coroutineLock = CoroutineLock()

    val storedBest = File("best.json")
    var running = true

    init {

        coroutine {
            coroutineLock.wait()


            if (storedBest.length() > 0) {
                val json = storedBest.readBytes().toString(Charset.defaultCharset())
                kotlin.io.println("Using $json")
                nn.setWeightsAndBiases(json.fromJson())
                return@coroutine
            }


            var generation = 0
            var lastBestDistance = 0f
            while (running) {


                population.evolve()

                generation++


                val currentBestDistance = population.best.second
                if (generation % 100 == 0) {
                    """
                        Generation : $generation
                        Best distance : ${population.best.second}
                    """.trimIndent().print()

                    if (!lastBestDistance.nearlyEquals(currentBestDistance)) {

                        lastBestDistance = currentBestDistance

                        nn = population.best.first.individual.copy()
                        world.reset()
                    }

//                    if (currentBestDistance > BEST_FINTESS - 10f) {
//                        val json = population.best.first.individual.serialise().toJson()
//                        storedBest.apply {
//                            delete()
//                            writeText(json)
//                        }
//                        return@coroutine
//                    }

                }
            }
        }

        onMouseClicked {
            "Saving".print
            val json = population.best.first.individual.serialise().toJson()
            storedBest.apply {
                delete()
                writeText(json)
            }
            "\tSaved".print
        }

        onKeyPressed { world.player.jump() }

    }

    override fun setup() {
        super.setup()
        frame.isResizable = true
    }

    override fun draw() {
        coroutineLock.unlock()
        background(0)
        noStroke()
        fill(255)

        nn.doNetworkDecision(world)

        with(world) {

            updatePhysics {
                background(255f, 0f, 0f)
                "Died after: ${world.frameCount}".print
                world.reset()
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
        Player(30.Xpx, 30.Ypx)
            .apply {
                body.position.set(0.1, 0.2)
            }
    }
    val pipes = mutableListOf<Pipe>()


    fun reset() {
        pipes.clear()
        pipes.add(Pipe())

        player.body.position.set(0.1, 0.2)
        player.body.velocity.reset()
        player.body.acceleration.reset()
        frameCount = 0
    }

    var frameCount = 0
    private val cutSize = 0.25f
    private val gravity = EPointMutable(0, (0.5).Ypx)


    fun updatePhysics(onCollide: () -> Unit) {
        frameCount++

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


    inner class Pipe(var x: Float = 1f, val cut: Float = random(cutSize, 1f - cutSize)) {
        val w = 40.Xpx

        val topRect = ERect()
        val botRect = ERect()
        fun update() {
            x -= 5.Xpx
            topRect.set(x, 0, w, cut - cutSize)
            topRect.rectAlignedOutside(
                aligned = EAlignment.bottomCenter,
                size = ESizeType(w, 1f),
                spacing = cutSize,
                buffer = botRect
            )
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