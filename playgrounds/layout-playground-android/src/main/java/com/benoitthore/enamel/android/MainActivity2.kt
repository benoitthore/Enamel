package com.benoitthore.enamel.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.android.examples.ELayoutTestingView

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ELayoutTestingView(this, true))
    }
}
