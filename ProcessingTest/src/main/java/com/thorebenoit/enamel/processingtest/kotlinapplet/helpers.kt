package com.thorebenoit.enamel.processingtest.kotlinapplet

import com.thorebenoit.enamel.geometry.allocate
import com.thorebenoit.enamel.geometry.primitives.EPointMutable
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPApplet
import processing.core.PApplet
import processing.core.PConstants
import java.awt.event.*


fun java.awt.Point.toEPoint() = allocate { EPointMutable(x, y) }
fun processing.event.MouseEvent.toEPoint() = allocate { EPointMutable(x, y) }


fun KotlinPApplet.createKeyListener() = object : KeyAdapter() {
    val applet = this@createKeyListener
    override fun keyTyped(p0: KeyEvent) {
        applet.postEvent(p0.toProcessing())
    }

    override fun keyPressed(p0: KeyEvent) {
        applet.postEvent(p0.toProcessing())
    }

    override fun keyReleased(p0: KeyEvent) {
        applet.postEvent(p0.toProcessing())
    }
}

fun KotlinPApplet.createMouseListener() = object : MouseAdapter() {
    val applet = this@createMouseListener

    override fun mouseReleased(p0: MouseEvent) {
        applet.postEvent(p0.toProcessing())
    }

    override fun mouseMoved(p0: MouseEvent) {
        applet.postEvent(p0.toProcessing())
    }

    override fun mouseEntered(p0: MouseEvent) {
        applet.postEvent(p0.toProcessing())
    }

    override fun mouseDragged(p0: MouseEvent) {
        applet.postEvent(p0.toProcessing())
    }

    override fun mouseClicked(p0: MouseEvent) {
        applet.postEvent(p0.toProcessing())
    }

    override fun mouseExited(p0: MouseEvent) {
        applet.postEvent(p0.toProcessing())
    }

    override fun mousePressed(p0: MouseEvent) {
        applet.postEvent(p0.toProcessing())
    }

    override fun mouseWheelMoved(p0: MouseWheelEvent) {
        applet.postEvent(p0.toProcessing())
    }
}

//Copied From Processing
fun KeyEvent.toProcessing(): processing.event.KeyEvent {
    val event = this
    var peAction = 0
    when (event.getID()) {
        java.awt.event.KeyEvent.KEY_PRESSED -> peAction = processing.event.KeyEvent.PRESS
        java.awt.event.KeyEvent.KEY_RELEASED -> peAction = processing.event.KeyEvent.RELEASE
        java.awt.event.KeyEvent.KEY_TYPED -> peAction = processing.event.KeyEvent.TYPE
    }

//    int peModifiers = event.getModifiersEx() &
//      (InputEvent.SHIFT_DOWN_MASK |
//       InputEvent.CTRL_DOWN_MASK |
//       InputEvent.META_DOWN_MASK |
//       InputEvent.ALT_DOWN_MASK);
    val peModifiers = event.modifiers and (InputEvent.SHIFT_MASK or
            InputEvent.CTRL_MASK or
            InputEvent.META_MASK or
            InputEvent.ALT_MASK)

    return processing.event.KeyEvent(
        event, event.getWhen(),
        peAction, peModifiers,
        event.keyChar, event.keyCode
    )
}

/*
/!\ Doesn't take scaling into account cause it needs   processing.awt.PSurfaceAWT
 */
fun MouseEvent.toProcessing(surface: processing.awt.PSurfaceAWT? = null): processing.event.MouseEvent {
    val nativeEvent = this
    val windowScaleFactor = 1

    // the 'amount' is the number of button clicks for a click event,
    // or the number of steps/clicks on the wheel for a mouse wheel event.
    var peCount = nativeEvent.getClickCount()

    var peAction = 0
    when (nativeEvent.getID()) {
        java.awt.event.MouseEvent.MOUSE_PRESSED -> peAction = processing.event.MouseEvent.PRESS
        java.awt.event.MouseEvent.MOUSE_RELEASED -> peAction = processing.event.MouseEvent.RELEASE
        java.awt.event.MouseEvent.MOUSE_CLICKED -> peAction = processing.event.MouseEvent.CLICK
        java.awt.event.MouseEvent.MOUSE_DRAGGED -> peAction = processing.event.MouseEvent.DRAG
        java.awt.event.MouseEvent.MOUSE_MOVED -> peAction = processing.event.MouseEvent.MOVE
        java.awt.event.MouseEvent.MOUSE_ENTERED -> peAction = processing.event.MouseEvent.ENTER
        java.awt.event.MouseEvent.MOUSE_EXITED -> peAction = processing.event.MouseEvent.EXIT
        //case java.awt.event.MouseWheelEvent.WHEEL_UNIT_SCROLL:
        java.awt.event.MouseEvent.MOUSE_WHEEL -> {
            peAction = processing.event.MouseEvent.WHEEL
            /*
      if (preciseWheelMethod != null) {
        try {
          peAmount = ((Double) preciseWheelMethod.invoke(nativeEvent, (Object[]) null)).floatValue();
        } catch (Exception e) {
          preciseWheelMethod = null;
        }
      }
      */
            peCount = (nativeEvent as MouseWheelEvent).wheelRotation
        }
    }

    //System.out.println(nativeEvent);
    //int modifiers = nativeEvent.getModifiersEx();
    // If using getModifiersEx(), the regular modifiers don't set properly.
    val modifiers = nativeEvent.getModifiers()

    val peModifiers = modifiers and (InputEvent.SHIFT_MASK or
            InputEvent.CTRL_MASK or
            InputEvent.META_MASK or
            InputEvent.ALT_MASK)

    // Windows and OS X seem to disagree on how to handle this. Windows only
    // sets BUTTON1_DOWN_MASK, while OS X seems to set BUTTON1_MASK.
    // This is an issue in particular with mouse release events:
    // http://code.google.com/p/processing/issues/detail?id=1294
    // The fix for which led to a regression (fixed here by checking both):
    // http://code.google.com/p/processing/issues/detail?id=1332
    var peButton = 0
//    if ((modifiers & InputEvent.BUTTON1_MASK) != 0 ||
//        (modifiers & InputEvent.BUTTON1_DOWN_MASK) != 0) {
//      peButton = LEFT;
//    } else if ((modifiers & InputEvent.BUTTON2_MASK) != 0 ||
//               (modifiers & InputEvent.BUTTON2_DOWN_MASK) != 0) {
//      peButton = CENTER;
//    } else if ((modifiers & InputEvent.BUTTON3_MASK) != 0 ||
//               (modifiers & InputEvent.BUTTON3_DOWN_MASK) != 0) {
//      peButton = RIGHT;
//    }
    if (modifiers and InputEvent.BUTTON1_MASK != 0) {
        peButton = PConstants.LEFT
    } else if (modifiers and InputEvent.BUTTON2_MASK != 0) {
        peButton = PConstants.CENTER
    } else if (modifiers and InputEvent.BUTTON3_MASK != 0) {
        peButton = PConstants.RIGHT
    }

    // If running on Mac OS, allow ctrl-click as right mouse. Prior to 0215,
    // this used isPopupTrigger() on the native event, but that doesn't work
    // for mouseClicked and mouseReleased (or others).
    if (PApplet.platform == PConstants.MACOSX) {
        //if (nativeEvent.isPopupTrigger()) {
        if (modifiers and InputEvent.CTRL_MASK != 0) {
            peButton = PConstants.RIGHT
        }
    }


    return processing.event.MouseEvent(
        nativeEvent, nativeEvent.getWhen(),
        peAction, peModifiers,
        nativeEvent.getX() / windowScaleFactor,
        nativeEvent.getY() / windowScaleFactor,
        peButton,
        peCount
    )
}
