package com.thorebenoit.enamel.android

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.thorebenoit.enamel.android.dsl.*
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

                val tv1 = textView("TextView1".bold) {
                    padding = 16.dp
                    textSize = 20f

                    textColor = Color.WHITE
                    backgroundColor = Color.BLACK
                }

                val tv2 = textView("TextView2".bold.italic) {
                    padding = 16.dp
                    textSize = 20f

                    textColor = Color.WHITE
                    backgroundColor = Color.BLUE
                }



                constraints {
                    tv1 centerIn parentId
                    tv1.height(wrapContent)
                    tv1.width(wrapContent)


                    tv2 centerHorizontallyIn parentId
                    tv2.connect(TOP to BOTTOM of tv1)

                    tv2.height(wrapContent)
                    tv2.width(wrapContent)
                }


            }.frameLayoutLP(matchParent, matchParent) {
                gravity = Gravity.CENTER
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
}


/////////////////////
/////////////////////
/////////////////////
