package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.animations.lerp
import com.thorebenoit.enamel.kotlin.core.colorHSL
import com.thorebenoit.enamel.kotlin.core.math.OpenSimplexNoise
import com.thorebenoit.enamel.kotlin.core.math.Scale
import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.time.EDeltaTimer
import com.thorebenoit.enamel.kotlin.core.time.ETimerAnimator
import com.thorebenoit.enamel.kotlin.geometry.*
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.primitives.*
import com.thorebenoit.enamel.processingtest.examples.NoiseAndLerpTest
import processing.core.PApplet


object ProcessingTestMain {
    @JvmStatic
    fun main(args: Array<String>) {
        PApplet.main(NoiseAndLerpTest::class.java)
    }

}

/*
These function are used to help drawing only, they shouldn't be put in the library yet
 */
private operator fun ESizeImmutable.div(n: Number) = ESize(width / n.f, height / n.f)

private operator fun EPointType.div(n: Number) = EPoint(x / n.f, y / n.f)

///
class MainApplet : KotlinPApplet() {


}


