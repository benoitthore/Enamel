package com.benoitthore.enamel.geometry.layout.dsl

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.EStackLayout

fun List<ELayout>.stacked(alignment: EAlignment, spacing: Number = 0) =
    EStackLayout(this.toMutableList(), alignment, spacing)

fun List<ELayout>.stackedTopLeft(spacing: Number = 0) = stacked(EAlignment.topLeft, spacing)
fun List<ELayout>.stackedTopCenter(spacing: Number = 0) = stacked(EAlignment.topCenter, spacing)
fun List<ELayout>.stackedTopRight(spacing: Number = 0) = stacked(EAlignment.topRight, spacing)
fun List<ELayout>.stackedRightTop(spacing: Number = 0) = stacked(EAlignment.rightTop, spacing)
fun List<ELayout>.stackedRightCenter(spacing: Number = 0) = stacked(EAlignment.rightCenter, spacing)
fun List<ELayout>.stackedRightBottom(spacing: Number = 0) = stacked(EAlignment.rightBottom, spacing)
fun List<ELayout>.stackedBottomLeft(spacing: Number = 0) = stacked(EAlignment.bottomLeft, spacing)
fun List<ELayout>.stackedBottomCenter(spacing: Number = 0) = stacked(EAlignment.bottomCenter, spacing)
fun List<ELayout>.stackedBottomRight(spacing: Number = 0) = stacked(EAlignment.bottomRight, spacing)
fun List<ELayout>.stackedLeftTop(spacing: Number = 0) = stacked(EAlignment.leftTop, spacing)
fun List<ELayout>.stackedLeftCenter(spacing: Number = 0) = stacked(EAlignment.leftCenter, spacing)
fun List<ELayout>.stackedLeftBottom(spacing: Number = 0) = stacked(EAlignment.leftBottom, spacing)