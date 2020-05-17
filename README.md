# Enamel

Enamel is a set of libraries used to facilitate drawing, focusing on Android even though not some modules are Kotlin/JVM.

#  Adding the dependencies

Adding this to you gradle file:

`TODO`

# Documentation
[Generic documentation](docs/doc.md)

[List of Enamel objects](TODO)

[CanSetBounds/HasBounds extensions](TODO)

[Use Style and avoid Paints](TODO)

# Quick demo
Drawing of basic shapes and use of style:

```
    // Create a style, red filling an 8dp black border
    val myStyle: EStyle = EStyle(fill = Mesh(Color.RED), border = Mesh(Color.BLACK).toBorder(8.dp))

    // Create a rectangle and give it this style
    val rectangle = RectVisualEntity(myStyle)

    // Create a circle and give it this style, but with a yellow filling
    val circle = CircleVisualEntity(myStyle.copy(fill = Mesh(Color.YELLOW)))

    // Whenever the view bounds are changing
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        rectangle
            .setBounds(this) // Set the bounds of the rectangle to be the same as the View
            .selfInset(32.dp) // Add 32dp of padding on each side

        // Get the inner circle of the rectangle, and put that value into our circle (no allocation)
        rectangle.innerCircle(target = circle)

        circle.selfInset(32.dp) // decrease the circle radius by 32dp
    }

    override fun onDraw(canvas: Canvas) {
        //Draw the rectangle
        rectangle.onDraw(canvas)
        //Draw the circle on top of it
        circle.onDraw(canvas)
    }
    
```

