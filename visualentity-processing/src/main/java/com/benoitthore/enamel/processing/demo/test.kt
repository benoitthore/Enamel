package com.benoitthore.enamel.processing.demo

import com.benoitthore.enamel.processing.KotlinPApplet
import com.benoitthore.enamel.processing.getViewBounds
import com.benoitthore.enamel.processing.runApplet
import com.benoitthore.enamel.processing.visualentity.drawVE
import com.benoitthore.enamel.processing.visualentity.toProcessing
import com.benoitthore.visualentity.DemoDrawer
import com.benoitthore.visualentity.DemoRunner

fun main() {
    runApplet<TmpApplet>()
}

class TmpApplet : KotlinPApplet() {

    override fun settings() {
        size(1000, 500)
    }

    override fun setup() {
        super.setup()
        surface.setResizable(true)
    }

    val INDEX = 0

    val drawer = DemoDrawer(DemoRunner(com.benoitthore.visualentity.Demos[INDEX])) { toProcessing() }

    override fun draw() {
        background(0)

        drawer.getDrawables(getViewBounds()).forEach {
            drawVE(it.toProcessing())
        }

    }
}
