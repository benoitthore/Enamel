package com.thorebenoit.enamel.processingtest.examples.genetics

import com.thorebenoit.enamel.kotlin.geometry.primitives.RandomPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.plus
import com.thorebenoit.enamel.processingtest.examples.steering.DotDrawer
import com.thorebenoit.enamel.processingtest.examples.steering.SteeringVehicle
import com.thorebenoit.enamel.processingtest.examples.steering.center

class GeneticPresenter(val view: DotDrawer) {

    init {
        view.dotList = MutableList(10) { SteeringVehicle(view.center + RandomPoint(100)) }
        view.update()
    }
}
