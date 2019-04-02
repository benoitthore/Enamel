package com.thorebenoit.enamel.kotlin.geometry.layout.dsl

import com.thorebenoit.enamel.kotlin.core.color.randomColor
import com.thorebenoit.enamel.kotlin.core.color.red
import com.thorebenoit.enamel.kotlin.core.color.withAlpha
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.kotlin.geometry.layout.ETrackingLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutTag

inline val String.layoutTag get() = ELayoutTag(this)
inline val List<String>.layoutTag get() = map { it.layoutTag }


fun ELayout.tracked(dst: ELayout) = ETrackingLayout(src = this, dst = dst)

fun ELayout.leaf(color: Int = red.withAlpha(0.25)) = ELayoutLeaf(color = color, child = this)