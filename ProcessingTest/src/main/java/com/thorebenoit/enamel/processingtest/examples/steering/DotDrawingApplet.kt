package com.thorebenoit.enamel.processingtest.examples.steering

import com.thorebenoit.enamel.geometry.GeometryBufferProvider
import com.thorebenoit.enamel.geometry.figures.ECircleMutable
import com.thorebenoit.enamel.geometry.figures.EPolygon
import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.geometry.primitives.EAngleMutable
import com.thorebenoit.enamel.geometry.primitives.EPoint
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet._onMouseClicked

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
    override val size: ESize get() = esize

    override var onScroll: (Int) -> Unit = {}
    override var mouseRadius: Float = 0f
    override var constraintFrame: ERect = ERect()


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
        if (constraintFrame == ERect.zero) {
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
        ECircleMutable(mousePosition, mouseRadius).draw()
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

    fun EPoint.drawAsDot(shape: List<EPolygon>, angle: EAngleMutable, color: Int = 0) {
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

