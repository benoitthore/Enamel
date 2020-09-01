package com.benoitthore.enamel.visualentity.android

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.*
import com.benoitthore.enamel.visualentity.android.utils.VisualEntityDrawer
import com.benoitthore.visualentity.style.EStyleable


fun ECircleVisualEntity.toAndroid(): ECircleVisualEntityAndroid {
    val copy = _copy()
    return ECircleVisualEntityAndroidImpl(
        copy,
        VisualEntityDrawer(
            style
        ) { canvas, paint ->
            canvas.draw(
                copy,
                paint
            )
        })
}

interface ECircleVisualEntityAndroid : AndroidVisualEntity<ECircle>,
    ECircleVisualEntity {
    override fun _copy(): ECircleVisualEntityAndroid
}


internal class ECircleVisualEntityAndroidImpl(
    private val circle: ECircle,
    override val drawer: VisualEntityDrawer
) :
    ECircleVisualEntityAndroid,
    ECircle by circle,
    EStyleable by drawer {

    override val transform: ETransform = E.Transform()
    override fun _copy(): ECircleVisualEntityAndroid = circle._copy().toVisualEntity(style).toAndroid()
}