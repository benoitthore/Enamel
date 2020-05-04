package com.benoitthore.enamel.geometry.clipping

import com.benoitthore.enamel.geometry.clipping.ClippingLayer.ClippingType
import com.benoitthore.enamel.geometry.primitives.ETransformation

import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.geometry.svg.SVGContext
import com.benoitthore.enamel.geometry.svg.addTo

class ClippingLayer(
    val shape: ESVG,
    val transformation: ETransformation?,
    val clippingType: ClippingType
) : ESVG by shape {
    enum class ClippingType {
        In, Out;

        val isClipOut get() = this == Out
    }

}

class ClippingLayerList<T : ESVG> internal constructor(
    val shape: T,
    private val list: MutableList<ClippingLayer>
) :
    List<ClippingLayer> by list, ESVG {

    constructor(shape: T) : this(shape, mutableListOf())

    override fun addTo(context: SVGContext) {
        list.addTo(context)
    }

    fun clipOut(other: ESVG, transformation: ETransformation? = null) =
        apply { list.add(ClippingLayer(other, transformation, ClippingType.Out)) }

    fun clipIn(other: ESVG, transformation: ETransformation? = null) =
        apply { list.add(ClippingLayer(other, transformation, ClippingType.In)) }
}

fun <T : ESVG> T.clipOut(other: ESVG, transformation: ETransformation? = null) =
    ClippingLayerList(this).clipOut(other, transformation)

fun <T : ESVG> T.clipIn(other: ESVG, transformation: ETransformation? = null) =
    ClippingLayerList(this).clipIn(other, transformation)