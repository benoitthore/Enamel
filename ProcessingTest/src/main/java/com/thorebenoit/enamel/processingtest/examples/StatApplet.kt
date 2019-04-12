package com.thorebenoit.enamel.processingtest.examples

import com.thorebenoit.enamel.core.capture
import com.thorebenoit.enamel.kotlin.core.color.color
import com.thorebenoit.enamel.kotlin.core.executeInTerminal
import com.thorebenoit.enamel.core.math.i
import com.thorebenoit.enamel.geometry.alignement.EAlignment
import com.thorebenoit.enamel.geometry.figures.size
import com.thorebenoit.enamel.core.threading.coroutine
import com.thorebenoit.enamel.geometry.innerCircle
import com.thorebenoit.enamel.geometry.outterRect
import com.thorebenoit.enamel.processingtest.kotlinapplet.applet.KotlinPAppletModule
import com.thorebenoit.enamel.processingtest.kotlinapplet.modules.alwaysOnTop
import com.thorebenoit.enamel.processingtest.kotlinapplet.modules.draggableWindow
import com.thorebenoit.enamel.processingtest.kotlinapplet.modules.makeWindow
import org.intellij.lang.annotations.Language
import processing.core.PApplet
import processing.core.PConstants
import java.lang.Exception
import java.util.*


fun getCpuSpeed(): Float {
    val cmd = "top -F -n0 -l 1".executeInTerminal().split("\n").first { it.contains("CPU usage") }
    @Language("RegExp")
    val regex = "[0-9]+(\\.)?[0-9]*%".toRegex()

    var idlePercentage = regex.capture(cmd).last()
    idlePercentage = idlePercentage.substring(0, idlePercentage.length - 2)

    return 100f - idlePercentage.toFloat()
}


fun getCpuTemp(): Float {
    var temperature = ""
    @Language("RegExp")
    val regex = "[0-9]+\\.[0-9]+°C".toRegex()
    //TODO Improve this
    val cmd = "istats cpu temp".executeInTerminal()
        .split(" ").flatMap { it.split("\t") }
    temperature = cmd.mapNotNull { if (it.matches(regex)) it else null }.first()
    temperature = temperature.substring(0, temperature.length - 2)
    return temperature.toFloat()
}


data class StatData(
    val name: String,
    val color: Int,
    val pauseInSecond: Number = 1,
    val graphSize: Int = 10,
    val borderColor: Int = 0xFFFFFF.color,
    val formatData: (Float) -> String,
    val get: () -> Float
)

class StatApplet : KotlinPAppletModule() {

    companion object {
        fun start() {
            StatApplet.startWith(
                StatData(
                    name = "Speed",
                    color = 0xFF0000.color,
                    get = { getCpuSpeed() },
                    formatData = { "%.2f ".format(it) + '%' }
                ),
                StatData(
                    name = "Temperature",
                    color = 0x2FC5DE.color,
                    get = { getCpuTemp() },
                    formatData = { "%.2f °C".format(it) }
                )
            )
        }

        fun startWith(vararg statData: StatData, startDelay: Long = 2000) {
            dataList.addAll(statData.toList())
            while (dataList.isNotEmpty()) {
                PApplet.main(StatApplet::class.java)
                Thread.sleep(startDelay) // TODO Issue when creating too many applets at a time
            }
        }

        private val dataList: Queue<StatData> = LinkedList()
    }

    val data: StatData = dataList.remove()

    var text: String = "Loading"
    val list: Queue<Float> = LinkedList()

    init {

        coroutine {
            while (true) {
                try {
                    val value = data.get()
                    text = data.formatData(value)

                    list += value
                    while (list.size > data.graphSize) {
                        list.remove()
                    }

                    delay((data.pauseInSecond.toFloat() * 1000).i)
                    loop()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        onSettings {
            esize = 250 size 250
        }

        onSetup {
            frame.isResizable = true

        }

        // TODO transparentWindow is required for drawing to happen
        makeWindow(true)
        alwaysOnTop()
        draggableWindow()


        val border = 2

        onDraw {

            noStroke()

            fill(data.borderColor)
            val circle = eframe.innerCircle().draw()

            val drawingFrame = circle.outterRect()

            fill(0)
            circle.inset(border).draw()


            fill(255, 0, 0)
            // Make this functions easier to call
            val upperRect = drawingFrame
                .rectAlignedInside(EAlignment.leftCenter, size = drawingFrame.size.scale(1, 0.5))
                .toImmutable()

            val lowerRect = upperRect.offset(y = drawingFrame.height)


            fill(data.color)
            with(upperRect) {
                textAlign(PConstants.CENTER, PConstants.CENTER)
                text(text, center().x, center().y)
            }

            stroke(data.color)
            strokeWeight(2f)
            noFill()

            throw Exception("Fix me")
//            com.thorebenoit.enamel.geometry.figures.fit(lowerRect)
//                .draw(false)
        }
        noLoop()

    }
}
