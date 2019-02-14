package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.innerCircle
import com.thorebenoit.enamel.processingtest.examples.genetics.GeneticPresenter
import com.thorebenoit.enamel.processingtest.examples.steering.DotDrawingApplet
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet


object ProcessingTestMain {
    init {
        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false

    }

    @JvmStatic
    fun main(args: Array<String>) {
//        val applet = KotlinPApplet.createApplet<MainApplet>()
        val applet = KotlinPApplet.createApplet<DotDrawingApplet>()
        GeneticPresenter(applet)


    }

}

//
//
//
//
//class MainApplet : KotlinPApplet() {
//
//
//    override fun draw() {
//
//
//
//
//    }
//}