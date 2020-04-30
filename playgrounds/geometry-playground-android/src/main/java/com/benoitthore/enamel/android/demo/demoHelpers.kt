package com.benoitthore.enamel.android.demo

import android.content.res.Resources
import android.widget.SeekBar
import com.benoitthore.enamel.core.math.lerp

inline val Number.dp get() = toFloat() * Resources.getSystem().displayMetrics.density

fun SeekBar.onSeekChanged(block: (progress: Int) -> Unit) =
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            block(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    })


operator fun <E> List<E>.get(percentage: Float) =
    this[percentage.lerp(0, size - 1).toInt()]