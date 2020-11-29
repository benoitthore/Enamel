//package com.benoitthore.enamel.processing
//
//import com.benoitthore.enamel.geometry.builders.*
//import com.benoitthore.enamel.geometry.interfaces.innerCircle
//import com.benoitthore.enamel.geometry.interfaces.bounds.center
//import com.benoitthore.enamel.geometry.lerp
//import com.benoitthore.enamel.geometry.primitives.div
//import com.benoitthore.enamel.geometry.primitives.minus
//import com.benoitthore.enamel.geometry.primitives.size.size
//import com.benoitthore.enamel.geometry.interfaces.toCircle
//import com.benoitthore.visualentity.style.style
//import processing.core.PApplet
//
//fun main() {
//    PApplet.main(ESketch::class.java)
//}
//
//class ESketch : PApplet() {
//    private val rect: ERectVisualEntity by lazy {
//        Rect(size = width / 5 size height / 5).toVisualEntity(
//            style {
//                fillColor = 0x0096AE
//                borderWidth = 5f
//                borderColor = 0xff_ff_ff
//                borderWidth = 3
//            }
//        )
//    }
//
//    override fun settings() {
//        size(800, 800)
//    }
//
//    override fun draw() {
//        background(0)
//
//        rect.origin.lerp(0.05, rect.origin, mousePosition() - rect.size / 2)
//
//        drawVE(rect)
//
//        val maxCircleSize = rect.innerCircle().radius
//        rect.center()
//            .toCircle()
//            .apply {
//                radius = (maxCircleSize * center.normalizeIn(getViewBounds()).magnitude)
//                if (radius > maxCircleSize) {
//                    kotlin.io.println(radius)
//                }
//            }
//            .toVisualEntity(style { fillColor = 0xFF_0000 })
//            .draw()
//
//    }
//
//    fun <T : ProcessingVisualEntity> T.draw() = apply { drawVE(this@draw) }
//}
