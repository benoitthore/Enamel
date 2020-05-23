package com.benoitthore.enamel.processing

import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EStyleable
import processing.core.PApplet

class VisualEntityDrawer(val draw: (PApplet) -> Unit) : EStyleable {

    /**
     * Update only the necessary fields, since setMesh can allocate a gradient
     */
    override var style: EStyle = EStyle()

}