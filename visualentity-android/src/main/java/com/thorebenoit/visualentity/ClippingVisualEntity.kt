package com.thorebenoit.visualentity


import android.graphics.Canvas
import com.benoitthore.enamel.geometry.clipping.ClippingLayer
import com.benoitthore.enamel.geometry.clipping.ClippingLayerList
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.enamel.layout.android.PathSVGContext
import com.benoitthore.enamel.layout.android.clipOutPath
import com.benoitthore.enamel.layout.android.clipPath

fun <T : SVGVisualEntity> ClippingLayerList<T>.toVisualEntity() = ClippingVisualEntity(this)

class ClippingVisualEntity<T : SVGVisualEntity>(private val inputList: ClippingLayerList<T>) :
    VisualEntity by inputList.shape,
    ESVG by inputList.shape {

    private val shape = inputList.shape

    private val list: MutableList<Pair<ClippingLayer, PathSVGContext>> = mutableListOf()

    init {
        update()
    }

    fun update() {
        list.clear()

        inputList.forEach { layer ->
            list += layer to PathSVGContext(layer.shape)
        }
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