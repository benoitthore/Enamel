package com.benoitthore.enamel.layout.android.extract.layout

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.View
import com.benoitthore.enamel.geometry.layout.ELayout
import com.benoitthore.enamel.geometry.layout.flowed
import com.benoitthore.enamel.geometry.layout.refs.getAllChildren
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.layout.android.extract.SingleTouchDelegate
import com.benoitthore.enamel.layout.android.extract.dp

fun Drawable.imageLayout(paint: Paint = Paint()) = EImageLayout(drawable = this, paint = paint)
fun Bitmap.imageLayout(paint: Paint = Paint()) = EImageLayout(image = this, paint = paint)

// TODO Make 1 layout per line instead of 1 per word
// TODO Handle \n
// TODO Handle ellipsizing
fun CharSequence.wordLayout(
    paint: Paint,
    lineSpacing: Number = 8.dp,
    childSpacing: Number = 16.dp
) =
    split(" ").filter { it.isNotBlank() }.map { EWordLayout(paint, it) }
        .flowed(lineSpacing = lineSpacing, childSpacing = childSpacing)

fun ELayout.onClick(function: () -> Unit): EClickLayout = EClickLayout(this, function)

fun ELayout.addToView(view: View) {
    getAllChildren().filterIsInstance<ECanvasLayout>().forEach {
        if(it.viewParent == null){
            it.viewParent = view
        }else{
            throw Exception("$it already added to ${it.viewParent}")
        }
    }
}

fun ELayout.removeFromView(view: View) {
    getAllChildren().filterIsInstance<ECanvasLayout>().forEach { it.viewParent = view }
}

fun ELayout.setupClicks(view: View) {
    val touchLayouts = getAllChildren().filterIsInstance<EClickLayout>()

    val touchListener =
        SingleTouchDelegate { isDown: Boolean, current: EPoint?, previous: EPoint? ->
            touchLayouts.forEach {
                if (it.touchListener.invoke(isDown, current, previous)) {
                    view.performClick()
                    return@SingleTouchDelegate true
                }
            }
            return@SingleTouchDelegate false
        }

    view.setOnTouchListener(touchListener)
}
