package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.figures.rectgroup.ERectGroup
import com.benoitthore.enamel.geometry.figures.rectgroup.ERectGroupMutable
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformableMutable
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EStyleable

interface VisualEntity : ETransformableMutable, CanSetBounds, EStyleable

interface VisualEntityGroup<T : VisualEntity> : ERectGroup<T> {
    companion object {
        internal class VisualEntityGroupImpl<T : VisualEntity>(rectGroup: ERectGroup<T>) :
            VisualEntityGroup<T>,
            ERectGroup<T> by rectGroup
    }
}

fun <T : VisualEntity> List<T>.toVisualEntityGroup() : VisualEntityGroup<T> =
    VisualEntityGroup.Companion.VisualEntityGroupImpl(ERectGroupMutable.ERectGroupImpl(this))

