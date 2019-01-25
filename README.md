# Enamel (Work In Progress)
A set of tools and extensions for Android and Kotlin

# Content
## View DSL
Work in progress

## Extra Value Holder (backing field for extension property)
https://kotlinlang.org/docs/reference/extensions.html#extension-properties
When creating a extension property in Kotlin, the value can't be stored on the object since the value is resolved statically.
The ExtraValueHolder class allows you to store a value anyway, in a memory efficient manner (It won't create memory leaks)

Example:

```Kotlin
var Canvas.defaultPaint: Paint by ExtraValueHolder {
    Paint().apply { color = Color.RED } // default value
}
    ...
override fun onDraw(canvas: Canvas) {
    with(canvas) {
        drawLine(0f, 0f, 10f, 10f, defaultPaint)
    }
}
```


More documentation coming later...

Credits
https://github.com/AckeeCZ/anko-constraint-layout
https://github.com/screensailor
