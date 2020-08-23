package com.benoitthore.enamel.visualentity.android

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.*
import com.benoitthore.enamel.visualentity.android.utils.VisualEntityDrawer
import com.benoitthore.visualentity.style.EStyleable


fun EOvalVisualEntity.toAndroid(): OvalVisualEntityAndroid {
    val copy = copy()
    return OvalVisualEntityAndroidImpl(
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

interface OvalVisualEntityAndroid : AndroidVisualEntity<EOval>,
    EOvalVisualEntity {
    override fun copy(): OvalVisualEntityAndroid
}


internal class OvalVisualEntityAndroidImpl(
    private val oval: EOval,
    override val drawer: VisualEntityDrawer
) :
    OvalVisualEntityAndroid,
    EOval by oval,
    EStyleable by drawer {

    override val transform: ETransform = E.Transform()
    override fun copy(): OvalVisualEntityAndroid = oval.copy().toVisualEntity(style).toAndroid()
}