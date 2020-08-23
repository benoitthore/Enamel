package com.benoitthore.enamel.processing

import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EStyleable
import processing.core.PApplet

class VisualEntityDrawer(style: EStyle = EStyle(), val draw: (PApplet) -> Unit) : EStyleable {

    /**
     * Update only the necessary fields, since setMesh can allocate a gradient
     */
    override var style: EStyle = style
}