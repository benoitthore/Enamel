package com.thorebenoit.enamel.processingtest

import com.thorebenoit.enamel.kotlin.core.*
import com.thorebenoit.enamel.kotlin.core.math.i
import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.figures.fit
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.innerCircle
import com.thorebenoit.enamel.kotlin.geometry.outterRect
import com.thorebenoit.enamel.kotlin.geometry.primitives.point
import com.thorebenoit.enamel.kotlin.threading.coroutine
import com.thorebenoit.enamel.processingtest.examples.StatApplet
import com.thorebenoit.enamel.processingtest.examples.StatData
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletModule
import com.thorebenoit.enamel.processingtest.kotlinapplet.modules.*
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.Language
import processing.core.PApplet
import processing.core.PConstants
import java.lang.Exception
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


object ProcessingTestMain {
    @JvmStatic
    fun main(args: Array<String>) {

        // TODO Remove and check if allocating debug
        AllocationTracker.debugAllocations = false

//        PApplet.main(MainApplet::class.java)
    }

}
