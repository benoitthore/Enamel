package com.thorebenoit.enamel.processingtest.examples.steering

import com.thorebenoit.enamel.kotlin.geometry.GeometryBufferProvider
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.EPolygon
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.primitives.EAngle
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet._onMouseClicked


class SteeringApplet : KotlinPApplet(), DotDrawer {

    companion object {
        fun start() {
            val applet = KotlinPApplet.createApplet<SteeringApplet>()
            SteeringTestPresenter(applet)
        }
    }

    override lateinit var onMouseClicked: () -> Unit

    override var dotList: List<SteeringVehicle> = emptyList()
    override val size: ESizeType get() = esize

    override lateinit var onScroll: (Int) -> Unit
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

