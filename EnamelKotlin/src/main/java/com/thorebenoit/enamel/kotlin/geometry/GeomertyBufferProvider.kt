package com.thorebenoit.enamel.kotlin.geometry

import com.thorebenoit.enamel.kotlin.core.GenericBufferProvider
import com.thorebenoit.enamel.kotlin.geometry.figures.ECircle
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint


object GeomertyBufferProvider {
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