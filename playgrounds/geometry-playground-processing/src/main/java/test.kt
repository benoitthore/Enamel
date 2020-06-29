import processing.core.PApplet

fun main(){

    PApplet.main(TestApplet::class.java)
}

class TestApplet : PApplet() {
    override fun settings() {
        super.settings()
        size(500,500)
    }
}