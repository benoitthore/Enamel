package com.benoitthore.visualentity.style

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.builders.IE
import com.benoitthore.enamel.geometry.interfaces.bounds.EShape
import com.benoitthore.enamel.geometry.primitives.point.EPoint

/**
 * This doesn't need to be an extension function, but we're trying to avoid polluting the global namespace
 */
inline fun IE.style(crossinline block: StyleBuilder.() -> Unit): EStyle =
    StyleBuilder().apply(block).build()

/**
 * This doesn't need to be an extension function, but we're trying to avoid polluting the global namespace
 */
inline fun <T : EShape<T>> IE.style(shape: T, crossinline block: StyleBuilder.(T) -> Unit): EStyle =
    StyleBuilder().apply { block(shape) }.build()

class StyleBuilder {

    var fillColor: Int? = null
    var fillShader: EShader? = null
    var strokeColor: Int? = null
    var strokeShader: EShader? = null
    var strokeWidth: Number = 1f

    var shadowPositionY: Float
        get() = shadowPosition.y
        set(value) {
            _shadowPosition.y = value.toFloat()
        }

    val shadowPosition: EPoint get() = _shadowPosition

    private var _shadowPosition: EPoint = E.Point()

    fun build(): EStyle = EStyle(
        fill = buildFillMesh(),
        border = buildBorder(),
        shadow = buildShadow()
    )


    private fun buildFillMesh(): EMesh? =
        if (fillColor == null && fillShader == null) {
            null
        } else {
            EMesh(color = fillColor, shader = fillShader)
        }

    private fun buildBorder(): EStyle.Border? =
        if (strokeColor == null && strokeShader == null) {
            null
        } else {
            EStyle.Border(
                mesh = EMesh(color = strokeColor, shader = strokeShader),
                width = strokeWidth.toFloat()
            )
        }

    private fun buildShadow(): EStyle.Shadow? = null //TODO
}