package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.figures.rectgroup.ERectGroup
import com.benoitthore.enamel.geometry.figures.rectgroup.ERectGroupMutable
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.EShape
import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformable
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformableMutable
import com.benoitthore.enamel.geometry.svg.ESVG
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EStyleable
import com.benoitthore.visualentity.style.style
import java.awt.Color

interface VisualEntity<I, M> : ETransformable, HasBounds<I, M>, EStyleable
        where I : HasBounds<I, M>, M : CanSetBounds<I, M>

interface VisualEntityMutable<I, M> : ETransformableMutable, CanSetBounds<I, M>, VisualEntity<I, M>
        where I : HasBounds<I, M>, M : CanSetBounds<I, M>