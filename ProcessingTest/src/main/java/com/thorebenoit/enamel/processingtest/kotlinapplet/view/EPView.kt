package com.thorebenoit.enamel.processingtest.kotlinapplet.view

import com.thorebenoit.enamel.kotlin.core.backingfield.ExtraValueHolder
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRefObject
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.getRefs
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda
import java.util.concurrent.ConcurrentHashMap


//val <T : EPView> T.layoutRef: ELayoutRef<T> by ExtraValueHolder {
//    ELayoutRef(
//        ELayoutRefObject(this, { isAdded = true }, { isAdded = false }, { this == it }),
//        { size -> size },
//        { frame -> drawingRect.set(frame) }
//        )
//}


abstract class EPView(var tag: String?) {


    protected var drawingRect: ERect = ERect()

    open fun onLayout(frame: ERectType) {
        drawingRect.set(frame)
    }

    abstract fun onDraw(applet: KotlinPAppletLambda)
    open fun onMeasure(size: ESizeType) = size
}

class EmptyView : EPView(null) {
    override fun onDraw(applet: KotlinPAppletLambda) {

    }

}

class EPViewGroup(tag: String? = null) : EPView(tag) {

    val viewList = mutableSetOf<EPView>()
    var layout: ELayout? = null

    val children: MutableSet<EPView> = ConcurrentHashMap.newKeySet()

    override fun onLayout(frame: ERectType) {
        super.onLayout(frame)
        layout?.arrange(frame)
    }

    override fun onDraw(applet: KotlinPAppletLambda) {
        children.forEach {
            it.onDraw(applet)
        }
    }

}


private fun <T : EPView> T.createLayoutRef(parent: EPViewGroup): ELayoutRefObject<T> {
    val view = this
    return ELayoutRefObject(
        viewRef = view,
        addToParent = {
            parent.children.add(view)
        },
        removeFromParent = {
            parent.children.remove(view)
        },
        isSameView = { this.tag != null && this.tag == it.tag }
    )
}


fun <T : EPView> T.laidIn(parent: EPViewGroup): ELayoutRef<T> {
    val sizeBuffer = ESize()

    return ELayoutRef(
        this.createLayoutRef(parent),
        sizeToFit = { size ->
            ref.viewRef.onMeasure(size)
        },
        arrangeIn = { frame ->
            ref.viewRef.onLayout(frame)
        }
    )
}
