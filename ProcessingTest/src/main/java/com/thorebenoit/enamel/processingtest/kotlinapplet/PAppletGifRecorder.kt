package com.thorebenoit.enamel.processingtest.kotlinapplet

import com.squareup.gifencoder.GifEncoder
import com.squareup.gifencoder.ImageOptions
import processing.core.PApplet
import java.io.FileOutputStream

class PAppletGifRecorder(val applet: PApplet, val fileName: String = "test.gif") {

    //
    val outputStream = FileOutputStream("test.gif")
    val options = ImageOptions()
    val gifEncoder: GifEncoder by lazy {
        GifEncoder(outputStream, applet.width, applet.height, 0)
    }

    fun save() {
        applet.loadPixels()
        gifEncoder.addImage(applet.pixels, applet.width, options)
    }

    fun finish() {
        gifEncoder.finishEncoding()
        outputStream.close()
        System.exit(0)
    }

}