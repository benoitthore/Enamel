package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformable
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformableMutable
import com.benoitthore.visualentity.style.EStyleable

interface VisualEntity<I, M> : ETransformable, HasBounds<I, M>, EStyleable
        where I : HasBounds<I, M>, M : CanSetBounds<I, M>

interface VisualEntityMutable<I, M> : ETransformableMutable, CanSetBounds<I, M>, VisualEntity<I, M>
        where I : HasBounds<I, M>, M : CanSetBounds<I, M>


