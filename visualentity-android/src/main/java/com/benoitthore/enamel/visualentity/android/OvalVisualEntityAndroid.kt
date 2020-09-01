package com.benoitthore.enamel.visualentity.android

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.layout.android.draw
import com.benoitthore.visualentity.*
import com.benoitthore.enamel.visualentity.android.utils.VisualEntityDrawer
import com.benoitthore.visualentity.style.EStyleable


fun EOvalVisualEntity.toAndroid(): OvalVisualEntityAndroid {
    val copy = _copy()
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
    override fun _copy(): OvalVisualEntityAndroid
}


internal class OvalVisualEntityAndroidImpl(
    private val oval: EOval,
    override val drawer: VisualEntityDrawer
) :
    OvalVisualEntityAndroid,
    EOval by oval,
    EStyleable by drawer {

    override val transform: ETransform = E.Transform()
    override fun _copy(): OvalVisualEntityAndroid = oval._copy().toVisualEntity(style).toAndroid()
}