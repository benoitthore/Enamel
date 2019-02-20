package com.thorebenoit.enamel.processingtest.kotlinapplet.modules

import com.thorebenoit.enamel.kotlin.core.print
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.size
import com.thorebenoit.enamel.kotlin.geometry.primitives.unaryMinus
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletModule
import com.thorebenoit.enamel.processingtest.kotlinapplet.createKeyListener
import com.thorebenoit.enamel.processingtest.kotlinapplet.createMouseListener
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel

fun <T : KotlinPAppletModule> T.undecorated(): T = apply {
    onSetup {
        jframe.removeNotify()
        jframe.isUndecorated = true
        jframe.addNotify()
    }
}


fun <T : KotlinPAppletModule> T.alwaysOnTop(): T = apply {
    onSetup {
        jframe.removeNotify()
        jframe.isAlwaysOnTop = true
        jframe.addNotify()
    }
}

fun <T : KotlinPAppletModule> T.transparentWindow(): T = makeWindow(transparent = true)

fun <T : KotlinPAppletModule> T.makeWindow(transparent: Boolean = false): T = apply {
    lateinit var _frame: JFrame

    onSetup {
        _frame = jframe

        if (transparent) {
            _frame.removeNotify()
            _frame.isUndecorated = true
            _frame.layout = null
            _frame.addNotify()
        }

        val panel = object : JPanel() {
            override fun paintComponent(graphics: Graphics) {
                if (graphics is Graphics2D) {
                    // TODO Check if still works
                    graphics.drawImage(this@onSetup.graphics!!.image, 0, 0, null)
                }
            }
        }
        panel.setSize(width, height)

        val mouseListener = createMouseListener()
        val keyListener = createKeyListener()

        panel.addMouseListener(mouseListener)
        panel.addMouseMotionListener(mouseListener)
        panel.addKeyListener(keyListener)


        _frame.contentPane = panel
        _frame.setSize(width, height)
        this.frame = _frame

        onSizeChanged {
            // TODO Sometimes applet gets resized for no reason
//            (width size height).print
            _frame.setSize(width, height)
            panel.setSize(width, height)
        }
    }

    onPreDraw {
        background(0, 0f)
    }

    onPostDraw {
        if (transparent)
            _frame.background = Color(0, 0, 0, 0)
    }

}


// TODO https://stackoverflow.com/questions/24476496/drag-and-resize-undecorated-jframe
fun KotlinPAppletModule.draggableWindow(shouldDrag: () -> Boolean = { true }) = apply {

    onMouseDragged {
        if (shouldDrag()) {
            // TODO Offset
            val newPosition = mousePositionOnScreen.offset(-center)
            val newFrame = ERect(newPosition, esize)

            if (displayFrame.contains(newFrame)) {
                windowLocation = newPosition
            }

        }
    }
}

