package com.thorebenoit.enamel.processingtest.kotlinapplet.applet

import com.thorebenoit.enamel.processingtest.kotlinapplet.modules.jframe

abstract class KotlinPAppletModule : KotlinPAppletLambda() {

    override fun setup() {
        super.setup()
        jframe // init jframe
    }


}

