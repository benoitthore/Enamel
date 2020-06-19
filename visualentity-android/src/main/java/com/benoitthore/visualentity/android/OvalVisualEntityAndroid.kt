package com.benoitthore.visualentity.android

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.figures.oval.EOvalMutable
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.*
import com.benoitthore.visualentity.android.utils.VisualEntityDrawer
import com.benoitthore.visualentity.style.EStyleable

fun OvalVisualEntity.toAndroid(): OvalVisualEntityAndroid =
    toMutable().toAndroid()

fun OvalVisualEntityMutable.toAndroid(): OvalVisualEntityMutableAndroid {
    return OvalVisualEntityMutableAndroidImpl(
        this,
        VisualEntityDrawer { canvas, paint ->
            canvas.draw(
                this,
                paint
            )
        })
}

interface OvalVisualEntityAndroid : AndroidVisualEntity<EOval, EOvalMutable>,
    OvalVisualEntity {
    override fun toMutable(): OvalVisualEntityMutableAndroid
    override fun toImmutable(): OvalVisualEntityAndroid
}

interface OvalVisualEntityMutableAndroid : OvalVisualEntityAndroid,
    OvalVisualEntityMutable,
    AndroidVisualEntityMutable<EOval, EOvalMutable>, EOvalMutable


internal class OvalVisualEntityMutableAndroidImpl(
    private val oval: EOvalMutable,
    override val drawer: VisualEntityDrawer
) :
    OvalVisualEntityMutableAndroid,
    EOvalMutable by oval,
    EStyleable by drawer {

    override val transform: ETransformMutable = E.TransformMutable()

    override fun toMutable(): OvalVisualEntityMutableAndroid =
        oval.toMutable().toVisualEntity(style).toAndroid()

    override fun toImmutable(): OvalVisualEntityAndroid = toMutable()
}