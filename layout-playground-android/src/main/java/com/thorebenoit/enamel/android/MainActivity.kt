package com.thorebenoit.enamel.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thorebenoit.enamel.android.examples.demoView1
import com.thorebenoit.enamel.android.examples.helloWorld
import com.thorebenoit.enamel.android.examples.helloWorldTransition
import com.thorebenoit.enamel.layout_android.startServer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(helloWorld(this))
    }

}