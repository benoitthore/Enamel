package com.thorebenoit.enamel.android

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thorebenoit.enamel.android.dsl.customView
import com.thorebenoit.enamel.android.dsl.views.*
import com.thorebenoit.enamel.android.elayout.EDroidLayout
import com.thorebenoit.enamel.kotlin.core.color.*
import com.thorebenoit.enamel.kotlin.core.math.random
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
                backgroundColor = black

                (0 until 50).forEach {
                    customView<EDroidLayout>().frameLayoutLP {
                        width = random(0, screenSize.width).toInt()
                        height = random(0, screenSize.height).toInt()

                        setMargins(
                            random(-screenSize.width / 2, screenSize.width).toInt(),
                            random(-screenSize.height / 2, screenSize.height).toInt(),
                            0,
                            0
                        )
                    }
                }
            }
        )


    }
}