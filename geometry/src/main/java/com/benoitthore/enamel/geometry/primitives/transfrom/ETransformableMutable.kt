package com.benoitthore.enamel.geometry.primitives.transfrom

interface ETransformableMutable : ETransformable {
    override val transform: ETransformMutable
}