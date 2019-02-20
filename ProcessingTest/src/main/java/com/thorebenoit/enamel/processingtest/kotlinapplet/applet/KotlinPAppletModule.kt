package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircleType
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import com.thorebenoit.enamel.kotlin.geometry.toCircle
import com.thorebenoit.enamel.processingtest.kotlinapplet.modules.jframe
import processing.core.PConstants
import processing.core.PGraphics
import java.lang.NullPointerException

abstract class KotlinPAppletModule : KotlinPAppletLambda() {

    override fun setup() {
        super.setup()
        jframe // init jframe
    }


}

