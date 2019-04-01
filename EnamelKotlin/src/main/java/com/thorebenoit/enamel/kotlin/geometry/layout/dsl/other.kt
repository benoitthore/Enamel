package com.thorebenoit.enamel.kotlin.geometry.layout.dsl

import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.ETrackingLayout
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutTag

inline val String.layoutTag get() = ELayoutTag(this)
inline val List<String>.layoutTag get() = map { it.layoutTag }


fun ELayout.tracked(dst: ELayout) = ETrackingLayout(src = this, dst = dst)