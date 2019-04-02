package com.thorebenoit.enamel.kotlin.geometry.layout.dsl

import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.EStackLayout

fun List<ELayout>._stacked(alignment: EAlignment, spacing: Number = 0) = EStackLayout(this.toMutableList(), alignment, spacing)

fun List<ELayout>._stackedTopLeft(spacing: Number = 0) = _stacked(EAlignment.topLeft, spacing)
fun List<ELayout>._stackedTopCenter(spacing: Number = 0) = _stacked(EAlignment.topCenter, spacing)
fun List<ELayout>._stackedTopRight(spacing: Number = 0) = _stacked(EAlignment.topRight, spacing)
fun List<ELayout>._stackedRightTop(spacing: Number = 0) = _stacked(EAlignment.rightTop, spacing)
fun List<ELayout>._stackedRightCenter(spacing: Number = 0) = _stacked(EAlignment.rightCenter, spacing)
fun List<ELayout>._stackedRightBottom(spacing: Number = 0) = _stacked(EAlignment.rightBottom, spacing)
fun List<ELayout>._stackedBottomLeft(spacing: Number = 0) = _stacked(EAlignment.bottomLeft, spacing)
fun List<ELayout>._stackedBottomCenter(spacing: Number = 0) = _stacked(EAlignment.bottomCenter, spacing)
fun List<ELayout>._stackedBottomRight(spacing: Number = 0) = _stacked(EAlignment.bottomRight, spacing)
fun List<ELayout>._stackedLeftTop(spacing: Number = 0) = _stacked(EAlignment.leftTop, spacing)
fun List<ELayout>._stackedLeftCenter(spacing: Number = 0) = _stacked(EAlignment.leftCenter, spacing)
fun List<ELayout>._stackedLeftBottom(spacing: Number = 0) = _stacked(EAlignment.leftBottom, spacing)