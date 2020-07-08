package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.interfaces.bounds.EShape
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformable
import com.benoitthore.visualentity.style.EStyleable

interface VisualEntity<T : EShape<T>> : ETransformable, EShape<T>, EStyleable

