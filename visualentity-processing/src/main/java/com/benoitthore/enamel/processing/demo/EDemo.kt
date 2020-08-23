package com.benoitthore.enamel.processing.demo

import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.alignement.NamedPoint
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.functions.*
import com.benoitthore.enamel.geometry.primitives.div
import com.benoitthore.enamel.geometry.primitives.times
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
private val targetStyle = E.style { strokeColor = 0x0000FF }

private fun ERect.createTarget() = scaleAnchor(0.5).toRect().toVisualEntity(targetStyle)
private fun ERect.createFollower() = scaleAnchor(0.25, NamedPoint.topLeft).innerCircle().toVisualEntity(followerStyle)

val Demos = listOf(
    createDemoFunction("setBounds with Other EShape") { frame, progress ->

        val target = frame.createTarget()
        val follower = frame.createFollower()

        target.selfAlignInside(frame, center)

        if (progress > 0.5) {
            follower.setBounds(target)
        }

        listOf(target, follower)
    },
    createDemoFunction("setBounds align to other shape") { frame, progress ->

        val target = frame.createTarget()
        val follower = frame.createFollower()

        target.selfAlignInside(frame, center)

        if (progress > 0.5) {
            follower.selfAlignInside(target,center)
        }

        listOf(target, follower)
    }
)
