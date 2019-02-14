package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.GeometryBufferProvider
import com.thorebenoit.enamel.kotlin.geometry.figures.*
import com.thorebenoit.enamel.kotlin.geometry.innerCircle
import com.thorebenoit.enamel.kotlin.geometry.primitives.*
import com.thorebenoit.enamel.processingtest.examples.steering.DotDrawer
import com.thorebenoit.enamel.processingtest.examples.steering.SteeringApplet
import com.thorebenoit.enamel.processingtest.examples.steering.SteeringTestPresenter
import com.thorebenoit.enamel.processingtest.examples.steering.SteeringVehicle
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet


object ProcessingTestMain {
    init {
        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false

    }

    @JvmStatic
    fun main(args: Array<String>) {
//        val applet = KotlinPApplet.createApplet<MainApplet>()
        SteeringApplet.start()
    }

}



class MainApplet : KotlinPApplet() {


    override fun draw() {

        val mouseRect = ERectCenter(mousePosition,100,100).innerCircle()
        val frame = eframe.inset(200)

        if(frame.containsFull(mouseRect)){
            background(255)
        }else {
            background(255f,0f,0f)
        }

        noFill()


        mouseRect.draw()
        frame.draw()








    }
}