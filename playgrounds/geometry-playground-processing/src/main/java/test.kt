import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.lerp
import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rectgroup.toRectGroup
import com.benoitthore.enamel.processing.draw
import com.benoitthore.enamel.processing.getViewBounds
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

        val rect = E.RectMutable(0, 0, 50, 50)
        val rect2 = E.RectMutable(50, 50, 100, 100)

        listOf(rect, rect2).toRectGroup().selfAlignInside(getViewBounds(), EAlignment.center)
            .apply {

                width *= (mouseX.f / this@TestApplet.width).lerp(0.5, 2)
                height *= (mouseY.f / this@TestApplet.height).lerp(0.5, 2)
                rects.forEach { draw(it) }
            }

    }
}