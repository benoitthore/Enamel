package com.thorebenoit.enamel.processingtest.examples.steering

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.GeometryBufferProvider
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.EPolygon
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.primitives.EAngle
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet._onMouseClicked
import java.lang.Exception

fun main() {
    DotDrawingApplet.start()
}

class DotDrawingApplet : KotlinPApplet(), DotDrawer {

    private var onSizedCalled = false
    override var onSized: () -> Unit = {}
        set(value) {
            field = value
            if (esize.hasArea) {
                onSized()
            } else {
                onSizedCalled = false
                loop()
            }
        }

    companion object {
        fun start() {
            val applet = KotlinPApplet.createApplet<DotDrawingApplet>()
            SteeringTestPresenter(applet)
        }
    }

    override var onMouseClicked: () -> Unit = {}

    override var dotList: List<SteeringVehicle> = emptyList()
    set(value) {
        field = value
        update()
    }
    override val size: ESizeType get() = esize

    override var onScroll: (Int) -> Unit = {}
    override var mouseRadius: Float = 0f
    override var constraintFrame: ERectType = ERectType()


    init {

        _onMouseClicked {
            onMouseClicked()
        }
        onMouseWheel {
            onScroll(it.count)
        }
    }

    override fun update() {
        loop()
    }


    override fun draw() {
        if (constraintFrame == ERectType.zero) {
            constraintFrame = eframe
        }
        if (!onSizedCalled) {
            onSizedCalled = true
            onSized()
        }
        background(255)

        pushStyle()
        noFill()
        stroke(0)
        constraintFrame.draw()
        ECircle(mousePosition, mouseRadius).draw()
        popStyle()

        stroke(0)
        strokeWeight(1f)

        val bufferAngle = GeometryBufferProvider.angle()
        dotList.forEach { dot ->

            dot.position
                .let { shapeCenter ->

                    shapeCenter.drawAsDot(dot.shape, dot.body.velocity.heading(bufferAngle), dot.color)

                }
        }

        noLoop()
    }

    fun EPointType.drawAsDot(shape: List<EPolygon>, angle: EAngle, color: Int = 0) {
        pushMatrix()
        pushStyle()

        translate(x, y)


        rotate(angle.radians)

        fill(color)
        shape.forEach { it.points.draw(true) }

        fill(255, 0, 0)

        pushStyle()
        popMatrix()

    }


}

