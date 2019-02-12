package com.thorebenoit.enamel.processingtest.kotlinapplet.modules

import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import processing.awt.PSurfaceAWT
import javax.swing.JFrame

val KotlinPApplet.jframe
    get() = ((frame as? JFrame) ?: (surface.native as PSurfaceAWT.SmoothCanvas).frame as JFrame).apply {
        frame = this
    }