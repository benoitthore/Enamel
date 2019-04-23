package com.thorebenoit.enamel.android.examples

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import com.thorebenoit.enamel.android.dp
import com.thorebenoit.enamel.android.eViewGroup
import com.thorebenoit.enamel.geometry.alignement.EAlignment.*
import com.thorebenoit.enamel.geometry.layout.dsl.*
import com.thorebenoit.enamel.layout_android.EViewGroup
import com.thorebenoit.enamel.layout_android.withTag
import splitties.views.backgroundColor
import splitties.views.dsl.core.textView

private val TITLE = "title"
private val SUBTITLE = "subtitle"



fun helloWorld(context: Context): EViewGroup = context.eViewGroup {


    // Set the background color of the entire ViewGroup
    backgroundColor = Color.LTGRAY

    val title = context
        .textView {
            // define TextView
            text = "Title"
            textSize = 20f
            setTextColor(Color.WHITE)
            backgroundColor = Color.RED
        }
        .withTag(TITLE) // Add a transition tag
        .eLayoutRef() // create layout reference

    val subTitle = context
        .textView {
            // define TextView
            setTextColor(Color.WHITE)
            textSize = 18f
            backgroundColor = Color.BLUE
            text = "Sub Title"
        }
        .eLayoutRef() // create layout reference
        .withTag(SUBTITLE) // Add a transition tag

    listOf(title, subTitle)
        .stacked(bottomRight, 16.dp) // Stack subTitle on the bottom right corner of title
        .arranged(topRight) // Put to whole layout on the topLeft
}


fun helloWorldTransition(context: Context): EViewGroup {
    val viewGroup = helloWorld(context)

    // Get the layout tags
    val title = TITLE.layoutTag
    val subTitle = SUBTITLE.layoutTag

    // Define second layout
    val newLayout = listOf(title, subTitle)
        .stacked(bottomRight, 16.dp).snugged()        // Snug means wrap_content (horizontally in this case)
        .arranged(topLeft)
        .padded(16.dp)


    // Transition logic
    var defaultLayout = true

    val oldLayout = viewGroup.layout

    viewGroup.setOnClickListener {
        if (viewGroup.isInTransition) {
            Toast.makeText(context, "Already in transition", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }
        if (defaultLayout) {
            viewGroup.transitionTo(newLayout)
        } else {
            viewGroup.transitionTo(oldLayout)
        }

        defaultLayout = !defaultLayout
    }

    return viewGroup
}