package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.geometry.figures.ECircleMutable
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.primitives.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.EPointMutable

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
            GenericBufferProvider { ECircleMutable() }
        }
    }
    val rect by lazy {
        allocate {
            GenericBufferProvider { ERectMutable() }
        }
    }
    val angle by lazy {
        allocate {
            GenericBufferProvider { EAngleMutable() }
        }
    }
}