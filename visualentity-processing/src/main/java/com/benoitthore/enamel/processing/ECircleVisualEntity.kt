//package com.benoitthore.enamel.processing
//
//import com.benoitthore.enamel.geometry.builders.E
//import com.benoitthore.enamel.geometry.figures.circle.ECircle
//import com.benoitthore.enamel.geometry.figures.circle.ECircleMutable
//import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
//import com.benoitthore.visualentity.style.EStyle
//import com.benoitthore.visualentity.style.EStyleable
//
//fun ECircle.toVisualEntity(style: EStyle = EStyle()) = ECircleVisualEntity(to()).apply {
//    this.style = style
//}
//
//class ECircleVisualEntity internal constructor(
//    val shape: ECircleMutable,
//    override val drawer: VisualEntityDrawer = VisualEntityDrawer { papplet ->
//        papplet.draw(shape)
//    }
//) :
//    ProcessingVisualEntity,
//    EStyleable by drawer,
//    ECircleMutable by shape {
//    override val transform: ETransform = E.Transform()
//}
//
//
