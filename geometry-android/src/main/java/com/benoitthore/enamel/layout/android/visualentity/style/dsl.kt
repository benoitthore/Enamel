package com.benoitthore.enamel.layout.android.visualentity.style

import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.geometry.primitives.point


fun Mesh.asBorder(width: Number) = EStyle.Border(this, width.toFloat())
fun Mesh.asShadow(n: Number) = asShadow(n point n)
fun Mesh.asShadow(xOff: Number, yOff: Number) = asShadow(xOff point yOff)
fun Mesh.asShadow(offset: EPoint) = asShadow(offset.toMutable())
fun Mesh.asShadow(offset: EPointMutable) = EStyle.Shadow(this, offset)



interface MeshBuilder {
    object Impl : MeshBuilder

    fun color(color: Int, alpha: Number = 1) =
        Mesh.Color(color = color, alpha = alpha.toFloat())
}