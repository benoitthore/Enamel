package com.thorebenoit.enamel.android

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thorebenoit.enamel.android.dsl.customView
import com.thorebenoit.enamel.android.dsl.views.backgroundColor
import com.thorebenoit.enamel.android.dsl.views.frameLayout
import com.thorebenoit.enamel.android.elayout.EDroidLayout
import com.thorebenoit.enamel.kotlin.core.color.dkGray
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize


private var screenSizeBuffer = ESize()
private var screenSizePointBuffer = Point()

val Activity.screenSize
    get() = with(screenSizePointBuffer) {
        windowManager.defaultDisplay.getSize(this)
        screenSizeBuffer.set(x, y)
    }

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            frameLayout {
                customView<EDroidLayout>()
                backgroundColor = dkGray
            }
        )


    }
}