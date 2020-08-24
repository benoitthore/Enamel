package com.benoitthore.enamel.processing.demo

import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.alignement.NamedPoint
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.line.line
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.functions.*
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.visualentity.ELineVisualEntity
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


private val followerStyle = E.style { strokeColor = 0xFFFF00 }
private val rectStyle = E.style { strokeColor = 0x0000FF }
private val debugStyle = E.style { strokeColor = 0xff0000 }
private val crossStyle = E.style { strokeColor = 0xff0000  ; strokeWidth = 2f}

private fun ERect.createRect() = scale(0.5).toRect().toVisualEntity(rectStyle)
private fun ERect.createFollower() =
    E.Circle().setOriginSize(size = E.SizeSquare(size.min / 4)).toVisualEntity(followerStyle)

private fun ERect.createCross(at: EPoint): List<ELineVisualEntity> {
    val frame = E.Rect.Square(size.min * 0.05)
    val l1 =
        frame.toLine(topCenter, bottomCenter)
    val l2 =
        frame.toLine(leftCenter, rightCenter)

    return listOf(l1, l2)
        .map {
            it.setCenter(at)
        }
        .map { it.toVisualEntity(crossStyle) }
}


val Demos = listOf(
    createDemoFunction("setBounds with other EShape") { frame, progress ->
        val rect = frame.createRect()
        val follower = frame.createFollower()

        follower.setBounds(rect)

        listOf(rect, follower)
    },
    createDemoFunction("setOrigin center") { frame, progress ->

        val rect = frame.createRect()
        val follower = frame.createFollower()

        val center = rect.getCenter()
        val cross = frame.createCross(at = center)
        follower.setOrigin(center)

        listOf(rect, follower) + cross
    }
)
