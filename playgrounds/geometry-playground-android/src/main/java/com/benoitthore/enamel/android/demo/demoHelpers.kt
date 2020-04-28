package com.benoitthore.enamel.android.demo

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.widget.SeekBar
import com.benoitthore.enamel.android.dp
import com.benoitthore.enamel.core.get
import com.benoitthore.enamel.core.math.Scale
import com.benoitthore.enamel.core.math.i
import com.benoitthore.enamel.core.math.lerp
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.primitives.rotations
import com.benoitthore.enamel.layout.android.extract.draw
import com.benoitthore.enamel.layout.android.extract.drawPointList

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