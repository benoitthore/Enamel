package com.thorebenoit.enamel.geometry

import com.thorebenoit.enamel.geometry.figures.ECircle
import com.thorebenoit.enamel.geometry.figures.ERect
import com.thorebenoit.enamel.geometry.primitives.EAngle
import com.thorebenoit.enamel.geometry.primitives.EPointMutable

/*
Object pool used to get geometry objects in the library in order to perform operations without allocating
 */
object GeometryBufferProvider {
    val point by lazy {
        allocate {
            GenericBufferProvider { EPointMutable() }
        }
    }
    val circle by lazy {
        allocate {
            GenericBufferProvider { ECircle() }
        }
    }
    val rect by lazy {
        allocate {
            GenericBufferProvider { ERect() }
        }
    }
    val angle by lazy {
        allocate {
            GenericBufferProvider { EAngle() }
        }
    }
}