package com.benoitthore.enamel.android.demo

import android.content.Context
import com.benoitthore.enamel.android.examples.demoView1
import com.benoitthore.enamel.android.randomColor
import com.benoitthore.enamel.geometry.layout.dsl.*
import com.benoitthore.enamel.geometry.alignement.*
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.layout_android.*
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