package com.benoitthore.enamel.geometry.builders

interface IE : PointBuilder, SizeBuilder, AngleBuilder, LineBuilder, RectBuilder, CircleBuilder,
    OffsetBuilder, OvalBuilder

/**
 * This interface can be used to create any kind of shape provided by this library
 */
object E : IE