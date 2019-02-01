package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.kotlin.geometry.AllocationTracker
import com.thorebenoit.enamel.kotlin.geometry.alignement.NamedPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointImmutable
import com.thorebenoit.enamel.kotlin.geometry.primitives.degrees
import com.thorebenoit.enamel.kotlin.geometry.toCircle
import com.thorebenoit.enamel.processingtest.KotlinPApplet

data class Segment(val start: EPoint, val end: EPoint) {
    fun divide(): List<Segment> {
        val length = start.distanceTo(end) / 3

        /*
              c
             / \
        a - b   d - e
         */
        val a = start.copy()
        val e = end.copy()

        val b = start.offsetTowards(end, length)
        val c = b.rotateBy((-60).degrees(), length) // TODO Fix
        val d = start.offsetTowards(end, length * 2)
//

        return listOf(
            Segment(a, b),
            Segment(b, c),
            Segment(c, d),
            Segment(d, e)
        )
    }
}

class FractalSnowApplet : KotlinPApplet() {


    init {
        AllocationTracker.debugAllocations = false
    }

    private val segments = mutableListOf<Segment>()
    override fun setup() {
        segments.add(
            Segment(
                eframe.pointAtAnchor(NamedPoint.topLeft),
                eframe.pointAtAnchor(NamedPoint.bottomRight)
            )
        )

        onMouseClicked { doDivide() }
    }

    private fun doDivide() {
        val newSegments = mutableListOf<Segment>()

        segments.forEach {
            newSegments += it.divide()
        }
        segments.clear()
        segments.addAll(newSegments)
    }

    override fun draw() {
        segments.forEach {
            it.start.toCircle(5).draw()
            it.end.toCircle(5).draw()
        }
    }

    fun Segment.draw() {
        line(start.x, start.y, end.x, end.y)
    }
}