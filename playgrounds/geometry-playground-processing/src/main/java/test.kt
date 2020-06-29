import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.innerCircle
import com.benoitthore.enamel.geometry.innerOval
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

        val frame = getViewBounds()

        val rect = E.RectMutable(0, 0, 100, 200).selfAlignInside(frame,EAlignment.center)

        draw(rect)
        draw(rect.innerOval())

        noLoop()
    }
}