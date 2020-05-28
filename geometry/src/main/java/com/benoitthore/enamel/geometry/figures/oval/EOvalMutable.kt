package com.benoitthore.enamel.geometry.figures.oval

import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.EShapeMutable

interface EOvalMutable : EOval, CanSetBounds<EOval, EOvalMutable>, EShapeMutable<EOval,EOvalMutable> {
    override var ry: Float
    override var rx: Float
}