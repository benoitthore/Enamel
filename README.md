# Motivation
The Android Canvas API is a powerful tool, but sometimes it can be very hard to use. This usually result in a lot of boilerplate, which makes the code harder to read and debug.

Using newer language features provided by Kotlin, this library intend to bring Android's Canvas as easy to use as possible.

# General concepts
Enamel is a set of libraries designed to facilitate the use of the Android **Canvas**. It consists of multiple modules.

- Core (Kotlin): Basic mathematical functions used by other modules.
- Geometry (Kotlin): Geometry shapes and concepts such a point, angle, rectangle, circle, etc.
- Geometry (Android): Plug for the *Geometry* module into Android, it contains extensions on Canvas related classes such as **Canvas**, **Path**, **View**.
- Touch (Android): Utility functions to handle touch event in an easy manner.
- Visual Entities (Android): Provides an easy way of drawing shapes defined with the *Geometry* module, abstracting away the **Paint** class.
- Animation (Android): Animation utilities for *Visual Entity*
- Layout (Kotlin): Layout system designed to work in any environment.
- Layout Canvas (Android) (coming soon): Bridge between the *Layout* module the Android SDK

#  Adding the dependencies

Adding this to you gradle file:

`TODO`

# Getting started
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

