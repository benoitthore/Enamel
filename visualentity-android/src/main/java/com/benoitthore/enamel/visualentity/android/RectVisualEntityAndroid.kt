package com.benoitthore.enamel.visualentity.android

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.*
import com.benoitthore.enamel.visualentity.android.utils.VisualEntityDrawer
import com.benoitthore.visualentity.style.EStyleable


fun ERectVisualEntity.toAndroid(): RectVisualEntityAndroid {
    val copy = copy()
    return RectVisualEntityAndroidImpl(
        copy(),
        VisualEntityDrawer(
            style
        ) { canvas, paint ->
            canvas.draw(
                copy,
                paint
            )
        })
}

interface RectVisualEntityAndroid : AndroidVisualEntity<ERect>,
    ERectVisualEntity {
    override fun copy(): RectVisualEntityAndroid
}


internal class RectVisualEntityAndroidImpl(
    private val rect: ERect,
    override val drawer: VisualEntityDrawer
) :
    RectVisualEntityAndroid,
    ERect by rect,
    EStyleable by drawer {

    override val transform: ETransform = E.Transform()
    override fun copy(): RectVisualEntityAndroid = rect.copy().toVisualEntity(style).toAndroid()
}