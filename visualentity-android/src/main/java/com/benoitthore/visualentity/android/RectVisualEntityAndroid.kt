package com.benoitthore.visualentity.android

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.*
import com.benoitthore.visualentity.android.utils.VisualEntityDrawer
import com.benoitthore.visualentity.style.EStyleable


fun RectVisualEntity.toAndroid(): RectVisualEntityAndroid =
    toMutable().toAndroid()

fun RectVisualEntityMutable.toAndroid(): RectVisualEntityMutableAndroid {
    return RectVisualEntityMutableAndroidImpl(
        this,
        VisualEntityDrawer(style) { canvas, paint ->
            canvas.draw(
                this,
                paint
            )
        })
}

interface RectVisualEntityAndroid : AndroidVisualEntity<ERect, ERect>,
    RectVisualEntity {
    override fun toMutable(): RectVisualEntityMutableAndroid
    override fun toImmutable(): RectVisualEntityAndroid
}

interface RectVisualEntityMutableAndroid : RectVisualEntityAndroid,
    RectVisualEntityMutable,
    AndroidVisualEntityMutable<ERect, ERect>,
    ERect


internal class RectVisualEntityMutableAndroidImpl(
    private val rect: ERect,
    override val drawer: VisualEntityDrawer
) :
    RectVisualEntityMutableAndroid,
    ERect by rect,
    EStyleable by drawer {

    override val transform: ETransformMutable = E.TransformMutable()

    override fun toMutable(): RectVisualEntityMutableAndroid =
        rect.toMutable().toVisualEntity(style).toAndroid()

    override fun toImmutable(): RectVisualEntityAndroid = toMutable()

    override fun reset() {
        rect.reset()
    }
}