package com.benoitthore.enamel.android.demo

import android.app.Activity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Space
import com.benoitthore.enamel.R
import com.benoitthore.enamel.android.dp
import com.benoitthore.enamel.android.onSeekChanged
import com.benoitthore.enamel.core.math.i

fun Activity.runDemo() {

    object {
        private var currentDemo = 0
            set(value) {
                val max = Demos.size - 1
                field = when {
                    value > max -> 0
                    value < 0 -> 0
                    else -> value
                }
                demoView(Demos[field])
            }

        init {
            setContentView(R.layout.activity_main)
            findViewById<Button>(R.id.previousButton).setOnClickListener {
                currentDemo--
            }
            findViewById<Button>(R.id.nextButton).setOnClickListener {
                currentDemo++
            }
            demoView(RectAlignmentAnchor_Rect)

            currentDemo = 0


        }

        private fun Activity.demoView(runner: DemoDrawer) {
            val demoView = findViewById<DemoView>(R.id.testView)

            demoView.demoRunner = runner

            findViewById<LinearLayout>(R.id.seekbar_holder).apply {

                removeAllViews()
                runner.progressLabels.forEachIndexed { i, label ->

                    addView(SeekBar(context).apply {
                        onSeekChanged { progress ->
                            demoView.setAnimatedValue(i, progress / 100f)
                        }

                        progress = 50
                    })


                    if (i < runner.progressLabels.size - 1) {
                        addView(Space(context), LinearLayout.LayoutParams(16.dp.i, 16.dp.i))
                    }

                }
            }

        }
    }
}