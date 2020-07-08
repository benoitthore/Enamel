package com.benoitthore.visualentity.android

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.*
import com.benoitthore.visualentity.android.utils.VisualEntityDrawer
import com.benoitthore.visualentity.style.EStyleable


fun CircleVisualEntity.toAndroid(): CircleVisualEntityAndroid {
    return CircleVisualEntityAndroidImpl(
        this,
        VisualEntityDrawer(style) { canvas, paint ->
            canvas.draw(
                this,
                paint
            )
        })
}

interface CircleVisualEntityAndroid : AndroidVisualEntity<ECircle>,
    CircleVisualEntity {
    override fun copy(): CircleVisualEntityAndroid
}


internal class CircleVisualEntityAndroidImpl(
    private val circle: ECircle,
    override val drawer: VisualEntityDrawer
) :
    CircleVisualEntityAndroid,
    ECircle by circle,
    EStyleable by drawer {

    override val transform: ETransform = E.TransformMutable()
    override fun copy(): CircleVisualEntityAndroid  = circle.copy().toVisualEntity(style).toAndroid()
}