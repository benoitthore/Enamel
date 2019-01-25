# Enamel (Work In Progress)
A set of tools and extensions for Android and Kotlin

#Content
##View DSL
Work in progress

## Extra Value Holder (backing field for extension function)
Example:

val Canvas.paint: Paint by ExtraValueHolder {
    Paint().apply { color = Color.RED } // default value
}
    ...
override fun onDraw(canvas: Canvas) {
    with(canvas) {
        drawLine(0f, 0f, 10f, 10f, paint)
    }
}
More documentation coming later...

Credits
https://github.com/AckeeCZ/anko-constraint-layout
https://github.com/screensailor
