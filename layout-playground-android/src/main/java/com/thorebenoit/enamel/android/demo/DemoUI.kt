package com.thorebenoit.enamel.android.demo

import android.content.Context
import com.thorebenoit.enamel.android.dp
import com.thorebenoit.enamel.android.eViewGroup
import com.thorebenoit.enamel.android.examples.demoView1
import com.thorebenoit.enamel.android.randomColor
import com.thorebenoit.enamel.geometry.layout.dsl.*
import com.thorebenoit.enamel.geometry.alignement.*
import com.thorebenoit.enamel.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.geometry.layout.ELayout
import com.thorebenoit.enamel.layout_android.EViewGroup
import com.thorebenoit.enamel.layout_android.laidIn
import com.thorebenoit.enamel.layout_android.startServer
import splitties.views.backgroundColor
import splitties.views.dsl.core.textView
import splitties.views.padding

class DemoUI(val context: Context) {

    val views: MutableMap<String, EViewGroup> = mutableMapOf()

    init {
        views += "KTS playground" to context.demoView1().startServer()
    }

    private var selected: String? = null

    fun onBackPressed(): Boolean {
        if (selected == null) {
            return false
        }
        home.transitionTo(defaultView(home))
        selected = null
        return true
    }

    val home: EViewGroup by lazy {
        context.eViewGroup {
            defaultView(this)
        }
    }

    private fun loadViewGroup(viewGroup: EViewGroup) {
        val newLayout = viewGroup.laidIn(home).arranged(topLeft)

        home.transitionTo(newLayout)
    }


    fun defaultView(view: EViewGroup): ELayout {
        return views
            .map { (str, vg) ->
                context.textView {
                    padding = 16.dp
                    backgroundColor = randomColor()

                    text = str
                    setOnClickListener {
                        selected = str
                        loadViewGroup(vg)
                    }
                }
            }
            .map { it.laidIn(view) }
            .stacked(bottomLeft, spacing = 16.dp)
            .padded(16.dp)
            .arranged(topLeft)
    }
}