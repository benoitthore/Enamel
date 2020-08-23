package com.benoitthore.enamel.processing.demo

import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.alignement.NamedPoint
import com.benoitthore.enamel.geometry.alignement.selfAlign
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.functions.*
import com.benoitthore.enamel.geometry.selfLerp
import com.benoitthore.visualentity.ECircleVisualEntity
import com.benoitthore.visualentity.ERectVisualEntity
import com.benoitthore.visualentity.VisualEntity
import com.benoitthore.visualentity.style.style
import com.benoitthore.visualentity.toVisualEntity

typealias EDemoFunction = (frame: ERect, progress: Float) -> List<VisualEntity<*>>

fun createDemoFunction(name: String, function: EDemoFunction): EDemo = object :
    EDemo {
    override val name: String get() = name

    override fun get(frame: ERect, progress: Float): List<VisualEntity<*>> =
        function.invoke(frame, progress)
}


interface EDemo {
    val name: String
    val animationSpeed: Long? get() = null
    fun get(frame: ERect, progress: Float): List<VisualEntity<*>>
}


private val followerStyle = E.style { strokeColor = 0xFF0000 }
private val rectStyle = E.style { strokeColor = 0x0000FF }

private fun ERect.createRect() = scaleAnchor(0.5).toRect().toVisualEntity(rectStyle)
private fun ERect.createFollower() =
    scaleAnchor(0.25, NamedPoint.topLeft).innerCircle().toVisualEntity(followerStyle)

data class DemoAgents(
    val staticShape: ERectVisualEntity,
    val mover: ECircleVisualEntity,
    val target: ECircleVisualEntity,
    val origin: ECircleVisualEntity
)

private fun createDemoAgents(frame: ERect) = DemoAgents(
    staticShape = frame.createRect().toVisualEntity { strokeColor = 0x0000FF }
        .selfAlignInside(frame, center),
    mover = frame.createFollower(),
    target = frame.createFollower().toVisualEntity { strokeColor = 0x00ff00 },
    origin = frame.createFollower().toVisualEntity { strokeColor = 0x00ff00 }
)

val Demos = listOf(
    createDemoFunction("TEST") { frame, progress ->
        val (staticShape, mover, target, origin) = createDemoAgents(frame)


        target.selfAlignInside(staticShape, center)

        mover.selfLerp(progress, from = mover, to = target)

        listOf(target, origin, staticShape, mover)
    },
    createDemoFunction("setBounds with Other EShape") { frame, progress ->
        val rect = frame.createRect()
        val follower = frame.createFollower()
        val target = frame.createFollower().toVisualEntity { strokeColor = 0x00ff00 }
        val origin = frame.createFollower().toVisualEntity { strokeColor = 0x00ff00 }

        rect.selfAlignInside(frame, center)

        target.selfAlignInside(rect, center)

        follower.selfLerp(progress, from = follower, to = target)

        listOf(target, origin, rect, follower)
    }
)
