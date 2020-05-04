package com.benoitthore.enamel.layout.android.visualentity.style

import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.geometry.primitives.point
import com.benoitthore.enamel.geometry.toMutable


fun Mesh.toBorder(width: Number) = EStyle.Border(this, width.toFloat())

fun Mesh.toShadow(n: Number) = toShadow(n point n)
fun Mesh.toShadow(xOff: Number, yOff: Number) = toShadow(xOff point yOff)
fun Mesh.toShadow(offset: EPoint) = toShadow(offset.toMutable())
fun Mesh.toShadow(offset: EPointMutable) = EStyle.Shadow(this, offset)

