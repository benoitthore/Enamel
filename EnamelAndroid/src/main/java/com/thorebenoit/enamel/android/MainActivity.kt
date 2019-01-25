package com.thorebenoit.enamel.android

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.thorebenoit.enamel.android.dsl.*
import com.thorebenoit.enamel.android.dsl.constraints.buildChain
import com.thorebenoit.enamel.android.dsl.constraints.constraints
import com.thorebenoit.enamel.android.dsl.views.*
import com.thorebenoit.enamel.kotlin.threading.coroutine

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        coroutine {

        }
        setContentView(TestView(this))
    }
}

class TestView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        enamelDelegate {
            constraintLayout {
                val textView = textView("Text")
                val button = button("Text")

                constraints {

//                    button.width(wrapContent).height(wrapContent)
//                    textView.width(wrapContent).height(wrapContent)

//                    buildChain {
//
//                        space(8.dp)
//
//                        +textView
//                        space(8.dp)
//
//                        +button
//                        space(8.dp)
//
//                        +frame
//                        space(8.dp)
//
//                        vertical = true
//                        packed()
//                    }
//                        ...
                    listOf(textView, button)
                        .buildChain {
                            defaultMargin = 8.dp

                            vertical = true
                            packed()
                        }
                        .forEach { it.alignParentStart(16.dp) }

                }

            }
        }

//           customView<TextView> {
//               backgroundColor = Color.YELLOW
//               padding = 32.dp
//               textSize = 26f
//               text = "OK"
//           }.frameLayoutLP(wrapContent, wrapContent) {
//               gravity = Gravity.CENTER
//           }
    }
}


/////////////////////
/////////////////////
/////////////////////
