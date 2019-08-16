package com.benoitthore.enamel.processingtest.kotlinapplet.applet

import com.benoitthore.enamel.processingtest.kotlinapplet.modules.jframe

abstract class KotlinPAppletModule : KotlinPAppletLambda() {

    override fun setup() {
        super.setup()
        jframe // init jframe
    }


}

