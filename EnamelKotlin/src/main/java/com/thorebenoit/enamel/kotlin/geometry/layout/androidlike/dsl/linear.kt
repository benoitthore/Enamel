package com.thorebenoit.enamel.kotlin.geometry.layout.androidlike.dsl

import com.thorebenoit.enamel.kotlin.geometry.alignement.EAlignment
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.androidlike.ELinearLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.androidlike.ESnugging
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.sized
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.width
import com.thorebenoit.enamel.kotlin.geometry.layout.dsl.height


fun List<ELayout>.stacked(
    alignment: EAlignment,
    gravity: EAlignment = EAlignment.topLeft,
    width: ESnugging = ESnugging.wrap,
    height: ESnugging = ESnugging.wrap,
    spacing: Number = 0

) = ELinearLayout(
    childLayouts = toMutableList(),
    alignment = alignment,
    gravity = gravity,
    width = width,
    height = height,
    spacing = spacing
)


fun List<ELayout>.stacked(
    alignment: EAlignment,
    gravity: EAlignment = EAlignment.topLeft,
    width: Number,
    height: Number,
    spacing: Number = 0

) = ELinearLayout(
    childLayouts = toMutableList(),
    alignment = alignment,
    gravity = gravity,
    width = ESnugging.fill,
    height = ESnugging.fill,
    spacing = spacing
).sized(width, height)


/////////////////////////


fun List<ELayout>.stacked(
    alignment: EAlignment,
    gravity: EAlignment = EAlignment.topLeft,
    width: ESnugging,
    height: Number,
    spacing: Number = 0

) = ELinearLayout(
    childLayouts = toMutableList(),
    alignment = alignment,
    gravity = gravity,
    width = width,
    height = ESnugging.fill,
    spacing = spacing
).height(height)


fun List<ELayout>.stacked(
    alignment: EAlignment,
    gravity: EAlignment = EAlignment.topLeft,
    width: Number,
    height: ESnugging,
    spacing: Number = 0
) = ELinearLayout(
    childLayouts = toMutableList(),
    alignment = alignment,
    gravity = gravity,
    width = ESnugging.fill,
    height = height,
    spacing = spacing
).width(width)



