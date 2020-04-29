//package com.benoitthore.enamel.geometry
//
//import com.benoitthore.enamel.geometry.e.E
//import com.benoitthore.enamel.geometry.primitives.EAngleMutable
//
///*
//Object pool used to get geometry objects in the library in order to perform operations without allocating
// */
//object GeometryBufferProvider {
//    val point by lazy {
//        allocate {
//            GenericBufferProvider { E.PointMutable() }
//        }
//    }
//    val circle by lazy {
//        allocate {
//            GenericBufferProvider { E.CircleMutable() }
//        }
//    }
//    val rect by lazy {
//        allocate {
//            GenericBufferProvider { E.RectMutable() }
//        }
//    }
//    val angle by lazy {
//        allocate {
//            GenericBufferProvider { EAngleMutable() }
//        }
//    }
//}