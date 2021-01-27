import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.lerp
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.alignedInside
import com.benoitthore.enamel.geometry.alignement.rectAlignedInside
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.Rect
import com.benoitthore.enamel.geometry.functions.fix
import com.benoitthore.enamel.geometry.functions.innerOval
import com.benoitthore.enamel.processing.*
import processing.core.PApplet

fun main() {
    PApplet.main(TestApplet::class.java)
}

class TestApplet : PApplet() {
    override fun settings() {
        super.settings()
        size(500, 500)
    }


    override fun draw() {
        background(50)
        fill(200f, 0f, 0f)
        strokeWeight(2f)
        stroke(0)

//        val rect = RectCenter(getViewCenter(), getViewSize()).padding(top = 100)
//        val oval = rect.innerOval()
//        draw(rect)
//        draw(oval)

        val rect = Rect(0, 0, 50, 50)
        val rect2 = Rect(50, 50, 100, 100)
        val applet = this

        listOf(rect, rect2).map {
            it.alignedInside(getViewBounds(), EAlignment.center)
        }
            .forEach {
                draw(it.fix())
                pushPop {
                    fill(0f, 255f, 0f)
                    draw(it.innerOval())
                }
            }
    }
}