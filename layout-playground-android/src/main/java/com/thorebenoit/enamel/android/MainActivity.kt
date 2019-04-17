package com.thorebenoit.enamel.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thorebenoit.enamel.android.examples.demoView1
import com.thorebenoit.enamel.layout_android.startServer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = demoView1()
        view.startServer()
        setContentView(view)
    }


}