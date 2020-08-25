package com.benoitthore.visualentity

import com.benoitthore.enamel.core.animations.Interpolator
import com.benoitthore.enamel.core.animations.sinInterpolator
import com.benoitthore.enamel.core.asList
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.functions.*
import com.benoitthore.enamel.geometry.primitives.plus
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.visualentity.style.style

class DemoRunner(val demo: EDemo, val interpolator: Interpolator = sinInterpolator) {


    var progress = 0f
        private set

    fun step(frame: ERect): List<VisualEntity<*>> {
        if (progress > 1f) {
            progress = 0f
        }

        progress += 0.01f
        return demo.get(frame, interpolator(progress))

    }
}
typealias EDemoFunction = (frame: ERect, progress: Float) -> List<VisualEntity<*>>

data class EDemo(
    val name: String,
    val animationSpeed: Long? = null,
    val get: (frame: ERect, progress: Float) -> List<VisualEntity<*>>
)

private val followerStyle = E.style { strokeColor = 0xFFFFFF00.i }
private val rectStyle = E.style { strokeColor = 0xFF0000FF.i }
private val debugStyle = E.style { strokeColor = 0xFFff0000.i }
private val crossStyle = E.style { strokeColor = 0xFFff0000.i; strokeWidth = 2f }
private val progressBarStyle = E.style { fillColor = 0xFFff0000.i }

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

class DemoDrawer<T : VisualEntity<*>>(
    private val runner: DemoRunner,
    private val toDrawable: VisualEntity<*>.() -> T
) {
    fun getDrawables(frame: ERect): List<T> {

        val progressBar = E.Rect(
            height = (frame.height * 0.005f).coerceAtLeast(1f),
            width = frame.width * runner.progress
        )
            .toVisualEntity(progressBarStyle)


        val frame = frame.inset(top = progressBar.height * 2)

        val visualEntities = runner.step(frame)

        return (visualEntities + progressBar).map(toDrawable)
    }
}


val Demos = listOf(
    EDemo("setBounds with other EShape") { frame, progress ->
        val rect = frame.createRect()
        val follower = frame.createFollower()

        follower.setBounds(rect)

        listOf(rect, follower)
    },
    EDemo("setOrigin center") { frame, progress ->

        val rect = frame.createRect()
        val follower = frame.createFollower()

        val center = rect.getCenter()
        val cross = frame.createCross(at = center)

        follower.setOrigin(center)

        listOf(rect, follower) + cross
    }
)
