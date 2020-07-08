//package com.benoitthore.enamel.processing
//
//import com.benoitthore.enamel.geometry.builders.E
//import com.benoitthore.enamel.geometry.figures.line.ELine
//import com.benoitthore.enamel.geometry.figures.line.ELineMutable
//import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
//import com.benoitthore.enamel.geometry.toMutable
//import com.benoitthore.visualentity.style.EStyle
//import com.benoitthore.visualentity.style.EStyleable
//import processing.core.PApplet
//
//
//fun ELine.toVisualEntity(style: EStyle = EStyle()) = ELineVisualEntity(toMutable()).apply {
//    this.style = style
//}
//
//class ELineVisualEntity internal constructor(
//    val shape: ELineMutable,
//    override val drawer: VisualEntityDrawer = VisualEntityDrawer { papplet ->
//        papplet.draw(shape)
//    }
//) :
//    ProcessingVisualEntity,
//    EStyleable by drawer,
//    ELineMutable by shape {
//    override val transform: ETransformMutable = E.TransformMutable()
//}
//
//
