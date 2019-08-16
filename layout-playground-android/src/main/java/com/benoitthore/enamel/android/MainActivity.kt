package com.benoitthore.enamel.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benoitthore.enamel.android.examples.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(helloWorld(this))
    }

}