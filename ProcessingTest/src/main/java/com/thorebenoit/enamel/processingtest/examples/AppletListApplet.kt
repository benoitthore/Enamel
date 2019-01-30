package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.processingtest.KotlinPApplet
import processing.core.PApplet
import processing.event.MouseEvent

class AppletListApplet : KotlinPApplet() {
    init {
        AllocationTracker.debugAllocations = false
    }

    val appletList = listOf(
        RadarApplet::class.java,
        BasicCock::class.java,
        RotatingCircle::class.java,
        PhysicsRainbowRain::class.java
    )

    var i = 0

    init {
        onMouseClicked {
            loadApplet(i++)
        }
    }

    private fun loadApplet(index: Int) {
        val clazz = appletList[index % appletList.size]
        PApplet.main(clazz)
    }

    override fun draw() {
        background(255)
    }
}


