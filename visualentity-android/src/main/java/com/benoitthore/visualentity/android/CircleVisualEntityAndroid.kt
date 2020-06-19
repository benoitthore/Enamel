package com.benoitthore.visualentity.android

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.figures.circle.ECircleMutable
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.*
import com.benoitthore.visualentity.android.utils.VisualEntityDrawer
import com.benoitthore.visualentity.style.EStyleable


fun CircleVisualEntity.toAndroid(): CircleVisualEntityAndroid =
    toMutable().toAndroid()

fun CircleVisualEntityMutable.toAndroid(): CircleVisualEntityMutableAndroid {
    return CircleVisualEntityMutableAndroidImpl(
        this,
        VisualEntityDrawer { canvas, paint ->
            canvas.draw(
                this,
                paint
            )
        })
}

interface CircleVisualEntityAndroid : AndroidVisualEntity<ECircle, ECircleMutable>,
    CircleVisualEntity {
    override fun toMutable(): CircleVisualEntityMutableAndroid
    override fun toImmutable(): CircleVisualEntityAndroid
}

interface CircleVisualEntityMutableAndroid : CircleVisualEntityAndroid,
    CircleVisualEntityMutable,
    AndroidVisualEntityMutable<ECircle, ECircleMutable>, ECircleMutable


internal class CircleVisualEntityMutableAndroidImpl(
    private val circle: ECircleMutable,
    override val drawer: VisualEntityDrawer
) :
    CircleVisualEntityMutableAndroid,
    ECircleMutable by circle,
    EStyleable by drawer {

    override val transform: ETransformMutable = E.TransformMutable()

    override fun toMutable(): CircleVisualEntityMutableAndroid =
        circle.toMutable().toVisualEntity(style).toAndroid()

    override fun toImmutable(): CircleVisualEntityAndroid = toMutable()
}