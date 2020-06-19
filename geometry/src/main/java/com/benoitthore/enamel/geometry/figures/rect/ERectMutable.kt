package com.benoitthore.enamel.geometry.figures.rect

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.EShapeMutable
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

interface ERectMutable : ERect, CanSetBounds<ERect, ERectMutable>, EShapeMutable<ERect,ERectMutable> {

    override val origin: EPointMutable
    override val size: ESizeMutable

    override var height: Float
    override var width: Float

}