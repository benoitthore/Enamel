package com.thorebenoit.enamel.android

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.thorebenoit.enamel.android.dsl.constraints.constraints
import com.thorebenoit.enamel.android.dsl.customView
import com.thorebenoit.enamel.android.dsl.enamelContext
import com.thorebenoit.enamel.android.dsl.views.*
import com.thorebenoit.enamel.android.elayout.ELayoutFrame
import com.thorebenoit.enamel.kotlin.core.color.*
import android.view.Gravity
import android.widget.TextView
import androidx.core.view.marginLeft
import com.thorebenoit.enamel.kotlin.core.math.random
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.io.Serializable

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
                customView<ELayoutFrame>()
                backgroundColor = black

                (0 until 50).forEach {
                    customView<ELayoutFrame>().frameLayoutLP {
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