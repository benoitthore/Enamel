package com.thorebenoit.enamel.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thorebenoit.enamel.android.demo.DemoUI
import com.thorebenoit.enamel.android.examples.demoView1
import com.thorebenoit.enamel.android.examples.helloWorld
import com.thorebenoit.enamel.android.examples.helloWorldTransition
import com.thorebenoit.enamel.layout_android.startServer

class MainActivity : AppCompatActivity() {

    val ui by lazy { DemoUI(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(helloWorld(this))
        setContentView(ui.home)
//        setContentView(demoView1())
    }

    override fun onBackPressed() {
        if (!ui.onBackPressed()) {
            super.onBackPressed()
        }
    }
}