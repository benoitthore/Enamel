package com.benoitthore.enamel.layout.android.visualentity


import android.graphics.Canvas
import com.benoitthore.enamel.geometry.clipping.ClippingLayer
import com.benoitthore.enamel.geometry.clipping.ClippingLayerList
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.layout.android.PathSVGContext
import com.benoitthore.enamel.layout.android.*

fun <T : SVGVisualEntity> ClippingLayerList<T>.toVisualEntity() = ClippingVisualEntity(this)
class ClippingVisualEntity<T : SVGVisualEntity>(list: ClippingLayerList<T>) :
    VisualEntity by list.shape,
    ESVG by list.shape {

    private val shape = list.shape
    private val list: List<Pair<ClippingLayer, PathSVGContext>> =
        list.map { layer ->
            layer to PathSVGContext(layer.shape)
        }

    override fun draw(canvas: Canvas) {
        canvas.withSave {
            list.forEach { (layer, path) ->
                if (layer.clippingType.isClipOut) {
                    canvas.clipOutPath(path)
                } else {
                    canvas.clipPath(path)
                }
            }
            shape.draw(canvas)
        }
    }

}