package com.thorebenoit.enamel.kotlin.geometry

import com.thorebenoit.enamel.kotlin.core.GenericBufferProvider
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint

/*
Object pool used to get geometry objects in the library in order to perform operations without allocating
 */
object GeometryBufferProvider {
    val point by lazy {
        allocate {
            GenericBufferProvider { EPoint() }
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
}