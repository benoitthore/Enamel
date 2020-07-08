package com.benoitthore.visualentity.android

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.*
import com.benoitthore.visualentity.android.utils.VisualEntityDrawer
import com.benoitthore.visualentity.style.EStyleable


fun OvalVisualEntity.toAndroid(): OvalVisualEntityAndroid {
    return OvalVisualEntityAndroidImpl(
        this,
        VisualEntityDrawer(style) { canvas, paint ->
            canvas.draw(
                this,
                paint
            )
        })
}

interface OvalVisualEntityAndroid : AndroidVisualEntity<EOval>,
    OvalVisualEntity {
    override fun copy(): OvalVisualEntityAndroid
}


internal class OvalVisualEntityAndroidImpl(
    private val oval: EOval,
    override val drawer: VisualEntityDrawer
) :
    OvalVisualEntityAndroid,
    EOval by oval,
    EStyleable by drawer {

    override val transform: ETransform = E.TransformMutable()
    override fun copy(): OvalVisualEntityAndroid  = oval.copy().toVisualEntity(style).toAndroid()
}