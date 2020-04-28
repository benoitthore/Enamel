package com.benoitthore.enamel.android

import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Space
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.R
import com.benoitthore.enamel.android.demo.*
import com.benoitthore.enamel.android.demo.DemoDrawer
import com.benoitthore.enamel.android.demo.DemoView
import com.benoitthore.enamel.core.math.i

inline val Number.dp get() = toFloat() * Resources.getSystem().displayMetrics.density

fun SeekBar.onSeekChanged(block: SeekBar.(progress: Int) -> Unit) =
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            seekBar.block(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    })

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(PrototypeView(this))

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

    private fun demoView(runner: DemoDrawer) {
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





















