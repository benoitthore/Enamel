package com.thorebenoit.enamel.processingtest.kotlinapplet.view

import com.thorebenoit.enamel.kotlin.core.backingfield.ExtraValueHolder
import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.layout.ELayout
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRef
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.ELayoutRefObject
import com.thorebenoit.enamel.kotlin.geometry.layout.refs.getRefs
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletLambda


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
}

class EmptyView : EPView(null) {
    override fun onDraw(applet: KotlinPAppletLambda) {

    }

}

class EPViewGroup(tag: String? = null) : EPView(tag) {

    var layout: ELayout? = null

    val children = mutableSetOf<EPView>()

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


fun <T : EPView> T.createLayoutRef(parent: EPViewGroup): ELayoutRefObject<T> {
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
    val view = this
    val sizeBuffer = ESize()

    return ELayoutRef(
        view.createLayoutRef(parent),
        sizeToFit = { size ->
            //            view.measure(
//                View.MeasureSpec.makeMeasureSpec(size.width.toInt(), View.MeasureSpec.AT_MOST),
//                View.MeasureSpec.makeMeasureSpec(size.height.toInt(), View.MeasureSpec.AT_MOST)
//            )
//            sizeBuffer.set(view.measuredWidth, view.measuredHeight)
            size
        },
        arrangeIn = { frame ->
            view.onLayout(frame)
        },
        _serialize = { serializer ->
            view.tag?.let {
                serializer.add(true)
                serializer.add(it)
            } ?: run {
                serializer.add(false)
            }
        },
        _deserialize = { deserializer ->
            val hasView = deserializer.readBool()
            if (hasView) {
                val tag = deserializer.readString()
                val layout = parent.layout!!

                val newView: T = parent.children.find { it.tag == tag } as T

                ref.removeFromParent()
                ref = newView.createLayoutRef(parent)
            }
        }
    )
}
