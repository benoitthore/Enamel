package com.benoitthore.enamel.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.android.examples.*
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.layout.dsl.justified
import com.benoitthore.enamel.geometry.layout.dsl.padded
import com.benoitthore.enamel.geometry.layout.dsl.snugged
import com.benoitthore.enamel.geometry.layout.dsl.stacked
import com.benoitthore.enamel.layout.android.dp
import com.benoitthore.enamel.layout.android.eViewGroup
import splitties.views.backgroundColor
import splitties.views.dsl.core.textView

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(ELayoutTestingView(this, true))

        val tv1 = textView { text = "First text view"; backgroundColor = randomColor() }
        val tv2 = textView { text = "Second text view"; backgroundColor = randomColor() }
        val view = eViewGroup {
            listOf(tv1, tv2).laid()
                .stacked(bottomLeft,16.dp)
                .snugged()
                .padded(16.dp)
        }
        setContentView(view)
    }

}
