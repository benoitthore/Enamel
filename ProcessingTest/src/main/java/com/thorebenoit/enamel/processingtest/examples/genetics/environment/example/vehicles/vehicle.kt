package com.thorebenoit.enamel.processingtest.examples.genetics.environment.example

import com.thorebenoit.enamel.core.fromJson
import com.thorebenoit.enamel.core.fromJsonSafe
import com.thorebenoit.enamel.core.math.Scale
import com.thorebenoit.enamel.core.math.f
import com.thorebenoit.enamel.core.math.lerp
import com.thorebenoit.enamel.core.of
import com.thorebenoit.enamel.core.print
import com.thorebenoit.enamel.geometry.figures.ELine
import com.thorebenoit.enamel.geometry.figures.ERayCast
import com.thorebenoit.enamel.geometry.figures.line
import com.thorebenoit.enamel.geometry.primitives.*
import com.thorebenoit.enamel.geometry.toCircle
import com.thorebenoit.enamel.kotlin.ai.genetics.Population
import com.thorebenoit.enamel.kotlin.ai.neurtalnetwork.ToyNeuralNetwork
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.physics.core.PhysicsBody
import com.thorebenoit.enamel.processingtest.examples.genetics.environment.*
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.pushPop
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.take
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.intellij.lang.annotations.Language
import java.util.*

val VIEWING_DISTANCE = 200
val NB_RAYS = 10
val POPULATION_SIZE = 20
val PRE_TRAIN = 0


class CDWorld(
    val walls: List<ELine>,
    val checkpoints: List<ELine>,
    val start: EPoint,
    val end: EPoint

) : GeneticWorld<CDVehicleController> {
    override var generationCount: Int = 0
    override val pop: Population<CDVehicleController> = makePop(POPULATION_SIZE) {
        CDVehicleController()
    }

    private var maxLoop: Int = 500
    private var loop: Int = 0

    override fun physicsLoop() {

        val list = pop.individuals.filter {
            it.individual.vehicle.let { v ->
                v.body.position.intersects(walls, v.radius) == null
            }
        }
        """

        """.trimIndent()


        if (list.isEmpty() || loop >= maxLoop) {

            if (loop >= maxLoop) {
                "MAXLOOP".print
            }

            val fitnessMap =
                pop.individuals.map { it to (it.individual.checkpoints.size / checkpoints.size.toFloat()).epsilonIfZero() }
                    .toMap()

            runBlocking {
                pop.evolve(fitnessMap)
            }

            reset()

            "Max fitness for generation $generationCount is ${fitnessMap.maxBy { it.value * checkpoints.size }?.value}".print
            generationCount++
            return
        }

        list.forEach {
            val ctrl = it.individual
            val vehicle = ctrl.vehicle
            val position = vehicle.body.position

            if (position.intersects(walls, vehicle.radius) == null) {
                ctrl.decide(walls)
                ctrl.vehicle.update()
            }

            val cp = position.intersects(checkpoints, vehicle.radius)
            if (cp != null) {
                ctrl.checkpoints += cp
            }
        }


        loop++
    }

    override fun reset() {
        loop = 0
        pop.individuals.forEach {
            val ctrl = it.individual
            ctrl.vehicle.body.position.set(start)
        }

        pop.individuals.forEach { it.individual.checkpoints.clear() }
    }

}

class CDVehicleController(
    val viewingDistance: Number = VIEWING_DISTANCE,
    val vehicle: CDVehicle = CDVehicle(),
    val nbRays: Int = NB_RAYS,
    val fov: EAngle = 360.degrees()
) : AppletDrawable by vehicle, NetworkController {

    val checkpoints = mutableSetOf<ELine>()
    private val sensors = nbRays.of { ERayCast() }

    // TODO RAYS + x velocity + y velocity
    override val network: ToyNeuralNetwork = ToyNeuralNetwork(nbRays + 3, 16, 2) // Speed, rotation

    // TODO Debug with this
    var detected: List<EPoint?> = emptyList()
        private set(value) {
            field = value
            vehicle.detected = value
        }


    fun decide(walls: List<ELine>) {

        val size = sensors.size.f

        detected = sensors
            .asSequence()
            .mapIndexed { i, rayCast ->
                rayCast.setPosition(vehicle.body.position)

                val angle = Scale.map(i, 0, size - 1, (-fov / 2).degrees, (fov / 2).degrees).degrees()
                rayCast.lookTowards(vehicle.angle + angle)

                rayCast.cast(walls)
            }.map {
                it?.let { hit ->
                    if (hit.distanceTo(vehicle.body.position) < viewingDistance.f) {
                        return@map hit
                    }
                }
                null
            }
            .toList()


        val inputs = (0 until nbRays).map {
            (detected[it]?.distanceTo(vehicle.body.position) ?: viewingDistance).f / viewingDistance.f
        } + (vehicle.body.velocity.x / vehicle.body.maxVelocity.f) + (vehicle.body.velocity.y / vehicle.body.maxVelocity.f) + vehicle.angle.rotation

        val result = network.feedForward(inputs)

        val direction = result[0].lerp(-1, 1)
        val rotation = result[1].rotation()

        vehicle.heading(rotation)
        if (direction > 0) {
            vehicle.forward()
        } else if (direction < 0) {
            vehicle.backwards()
        }
        // ELSE
        // if direction == 0 then don't move

    }
}

class CDVehicle(
    position: EPoint = EPoint(),
    angle: EAngle = EAngle(),
    val radius: Number = 25,
    maxSpeed: Number = 10f,
    val acceleration: Number = 1f,
    val angularSpeed: EAngle = 15.degrees() // TODO Add physics
) : AppletDrawable {

    var detected: List<EPoint?> = emptyList()

    override fun draw(applet: KotlinPAppletLambda) {
        applet.pushPop {
            body.position
                .apply {
                    noFill()
                    stroke(red)
                    toCircle(VIEWING_DISTANCE).draw()
                }
                .toCircle(radius)
                .toListOfPoint(3, startAt = EAngleMutable(angle))
                .apply {


                    noStroke()
                    fill(white)
                    draw()

                    fill(red)
                    first().draw()
                }

            detected.filterNotNull().forEach {
                noFill()
                stroke(red)
                (body.position line it).draw()
            }
        }
    }

    val body = PhysicsBody(maxSpeed, position.toMutable())


    fun update(deltaTime: Number = 1f) {
        body.update(deltaTime)
        body.velocity.selfMult(0.95)
    }

    fun forward() {
        body.addForce(EPoint(angle, acceleration))
    }

    fun backwards() {
//        body.addForce(EPoint(angle.inverse(), acceleration))
        slowDown()
    }

    // TODO Test
    fun slowDown() {
        body.addForce(-body.velocity)
    }

    fun turnLeft() {
        _angle.offset(-angularSpeed)
    }

    fun turnRight() {
        _angle.offset(angularSpeed)
    }

    fun heading(angle: EAngle) {
        _angle.set(angle)
    }

    private val _angle: EAngleMutable = EAngleMutable(angle)
    val angle: EAngle get() = _angle

}


class CDVehicleApplet : GenericGeneticsApplet<CDVehicleController>() {
    val start = 81 point 715.0
    val end = 712.0 point 66.0

    val walls =
        """
           [{"start":{"x":16.170563,"y":742.1164},"end":{"x":21.170563,"y":603.1164}},{"start":{"x":21.170563,"y":603.1164},"end":{"x":39.170563,"y":400.1164}},{"start":{"x":39.170563,"y":400.1164},"end":{"x":94.37842,"y":275.99963}},{"start":{"x":94.37842,"y":275.99963},"end":{"x":199.00009,"y":147.37817}},{"start":{"x":199.00009,"y":147.37817},"end":{"x":319.00006,"y":150.6987}},{"start":{"x":319.00006,"y":150.6987},"end":{"x":519.78284,"y":161.59927}},{"start":{"x":519.78284,"y":161.59927},"end":{"x":628.0,"y":287.99966}},{"start":{"x":628.0,"y":287.99966},"end":{"x":595.3594,"y":403.7299}},{"start":{"x":595.3594,"y":403.7299},"end":{"x":409.49646,"y":465.4021}},{"start":{"x":409.49646,"y":465.4021},"end":{"x":316.67743,"y":530.3365}},{"start":{"x":316.67743,"y":530.3365},"end":{"x":310.96954,"y":618.2548}},{"start":{"x":310.96954,"y":618.2548},"end":{"x":316.78308,"y":667.5995}},{"start":{"x":316.78308,"y":667.5995},"end":{"x":399.04077,"y":664.54785}},{"start":{"x":399.04077,"y":664.54785},"end":{"x":563.323,"y":559.66254}},{"start":{"x":563.323,"y":559.66254},"end":{"x":672.0305,"y":418.25418}},{"start":{"x":672.0305,"y":418.25418},"end":{"x":664.0305,"y":102.254166}},{"start":{"x":155.82944,"y":751.8836},"end":{"x":160.82944,"y":612.8836}},{"start":{"x":160.82944,"y":612.8836},"end":{"x":178.82944,"y":409.8836}},{"start":{"x":178.82944,"y":409.8836},"end":{"x":215.62158,"y":346.00037}},{"start":{"x":215.62158,"y":346.00037},"end":{"x":268.9999,"y":268.62183}},{"start":{"x":268.9999,"y":268.62183},"end":{"x":368.99994,"y":237.3013}},{"start":{"x":368.99994,"y":237.3013},"end":{"x":458.21716,"y":240.40073}},{"start":{"x":458.21716,"y":240.40073},"end":{"x":528.0,"y":288.00034}},{"start":{"x":528.0,"y":288.00034},"end":{"x":502.64062,"y":366.2701}},{"start":{"x":502.64062,"y":366.2701},"end":{"x":356.50354,"y":380.5979}},{"start":{"x":356.50354,"y":380.5979},"end":{"x":225.32259,"y":489.66348}},{"start":{"x":225.32259,"y":489.66348},"end":{"x":211.03046,"y":621.7452}},{"start":{"x":211.03046,"y":621.7452},"end":{"x":255.2169,"y":746.4005}},{"start":{"x":255.2169,"y":746.4005},"end":{"x":454.95923,"y":747.45215}},{"start":{"x":454.95923,"y":747.45215},"end":{"x":654.677,"y":600.33746}},{"start":{"x":654.677,"y":600.33746},"end":{"x":771.9695,"y":421.74582}},{"start":{"x":771.9695,"y":421.74582},"end":{"x":763.9695,"y":105.745834}}]
        """.trimIndent().fromJson<List<ELine>>()

    val checkpoints =
        """
            [{"start":{"x":16.170563,"y":742.1164},"end":{"x":155.82944,"y":751.8836}},{"start":{"x":18.670565,"y":672.6164},"end":{"x":158.32944,"y":682.3836}},{"start":{"x":21.170567,"y":603.1164},"end":{"x":160.82944,"y":612.8836}},{"start":{"x":21.170563,"y":603.1164},"end":{"x":160.82944,"y":612.8836}},{"start":{"x":30.170567,"y":501.6164},"end":{"x":169.82944,"y":511.3836}},{"start":{"x":39.17057,"y":400.1164},"end":{"x":178.82945,"y":409.8836}},{"start":{"x":39.170563,"y":400.1164},"end":{"x":178.82944,"y":409.8836}},{"start":{"x":66.77449,"y":338.058},"end":{"x":197.22551,"y":377.942}},{"start":{"x":94.37842,"y":275.99963},"end":{"x":215.62158,"y":346.00037}},{"start":{"x":94.37842,"y":275.99963},"end":{"x":215.62158,"y":346.00037}},{"start":{"x":146.68925,"y":211.6889},"end":{"x":242.31075,"y":307.3111}},{"start":{"x":199.00009,"y":147.37817},"end":{"x":268.9999,"y":268.62183}},{"start":{"x":199.00009,"y":147.37817},"end":{"x":268.9999,"y":268.62183}},{"start":{"x":259.00006,"y":149.03844},"end":{"x":318.99994,"y":252.96156}},{"start":{"x":319.00006,"y":150.6987},"end":{"x":368.99994,"y":237.3013}},{"start":{"x":319.00006,"y":150.6987},"end":{"x":368.99994,"y":237.3013}},{"start":{"x":419.39145,"y":156.14899},"end":{"x":413.60855,"y":238.85101}},{"start":{"x":519.78284,"y":161.59927},"end":{"x":458.21716,"y":240.40073}},{"start":{"x":519.78284,"y":161.59927},"end":{"x":458.21716,"y":240.40073}},{"start":{"x":573.8914,"y":224.79947},"end":{"x":493.10858,"y":264.20053}},{"start":{"x":628.0,"y":287.99966},"end":{"x":528.0,"y":288.00034}},{"start":{"x":628.0,"y":287.99966},"end":{"x":528.0,"y":288.00034}},{"start":{"x":611.6797,"y":345.86478},"end":{"x":515.3203,"y":327.13522}},{"start":{"x":595.3594,"y":403.7299},"end":{"x":502.64062,"y":366.2701}},{"start":{"x":595.3594,"y":403.7299},"end":{"x":502.64062,"y":366.2701}},{"start":{"x":502.42792,"y":434.56598},"end":{"x":429.57208,"y":373.434}},{"start":{"x":409.49646,"y":465.4021},"end":{"x":356.50354,"y":380.59787}},{"start":{"x":409.49646,"y":465.4021},"end":{"x":356.50354,"y":380.5979}},{"start":{"x":363.08694,"y":497.8693},"end":{"x":290.9131,"y":435.1307}},{"start":{"x":316.67743,"y":530.3365},"end":{"x":225.3226,"y":489.6635}},{"start":{"x":316.67743,"y":530.3365},"end":{"x":225.32259,"y":489.66348}},{"start":{"x":313.8235,"y":574.29565},"end":{"x":218.17651,"y":555.70435}},{"start":{"x":310.96954,"y":618.2548},"end":{"x":211.03046,"y":621.7452}},{"start":{"x":310.96954,"y":618.2548},"end":{"x":211.03046,"y":621.7452}},{"start":{"x":313.8763,"y":642.9271},"end":{"x":233.12369,"y":684.0729}},{"start":{"x":316.78308,"y":667.5995},"end":{"x":255.2169,"y":746.4005}},{"start":{"x":316.78308,"y":667.5995},"end":{"x":255.2169,"y":746.4005}},{"start":{"x":357.91193,"y":666.07367},"end":{"x":355.08807,"y":746.92633}},{"start":{"x":399.04077,"y":664.54785},"end":{"x":454.95923,"y":747.45215}},{"start":{"x":399.04077,"y":664.54785},"end":{"x":454.95923,"y":747.45215}},{"start":{"x":481.1819,"y":612.1052},"end":{"x":554.8181,"y":673.8948}},{"start":{"x":563.323,"y":559.66254},"end":{"x":654.677,"y":600.33746}},{"start":{"x":563.323,"y":559.66254},"end":{"x":654.677,"y":600.33746}},{"start":{"x":617.67676,"y":488.95837},"end":{"x":713.32324,"y":511.04163}},{"start":{"x":672.0305,"y":418.25418},"end":{"x":771.9695,"y":421.74582}},{"start":{"x":672.0305,"y":418.25418},"end":{"x":771.9695,"y":421.74582}},{"start":{"x":668.0305,"y":260.25418},"end":{"x":767.9695,"y":263.74585}},{"start":{"x":664.0305,"y":102.25418},"end":{"x":763.9695,"y":105.74585}}]
        """.trimIndent().fromJson<List<ELine>>()

    override val world = CDWorld(
        walls = walls,
        checkpoints = checkpoints,
        start = start,
        end = end
    )

    init {

        onSetup {
            world.fastForward(PRE_TRAIN)
        }

        onDraw {
            background(0)

            stroke(red)
            walls.forEach { it.draw() }

            drawWorld()

            stroke(white)
//            checkpoints.forEach { it.draw() }

            world.physicsLoop()

        }
    }

}

//
//
//
//
//
//
//
//
//
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
private inline fun <T, R : Comparable<R>> Iterable<T>.maxByWithIndex(selector: (T) -> R): Pair<Int, T>? {
    val iterator = iterator()

    if (!iterator.hasNext()) return null

    var maxElem = iterator.next()
    var maxValue = selector(maxElem)
    var maxIndex = 0

    var i = 1
    while (iterator.hasNext()) {
        val e = iterator.next()
        val v = selector(e)
        if (maxValue < v) {
            maxElem = e
            maxIndex = i
            maxValue = v
        }
        i++
    }
    return maxIndex to maxElem
}