package com.thorebenoit.enamel.geometry.layout.dsl

import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayoutLeaf
import com.thorebenoit.enamel.geometry.layout.ETrackingLayout
import com.thorebenoit.enamel.geometry.layout.refs.ELayoutTag

inline val String.layoutTag get() = ELayoutLeaf(0x00_00_00_00, ELayoutTag(this))
inline val List<String>.layoutTag get() = map { it.layoutTag }


fun ELayout.tracked(dst: ELayout) = ETrackingLayout(src = this, dst = dst)

fun ELayout.leaf(color: Int) = ELayoutLeaf(color = color, child = this)