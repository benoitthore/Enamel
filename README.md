# Enamel
If you're tired of Canvas code being too verbose, this is the solution you're looking for.

# Examples
**TODO add gifs**

# Concepts

## Shapes
Enamel currently features 4 basic shapes you can play with. More to come!
- Rectangle
- Circle
- Oval
- Line

## E
All the shapes are actually interfaces, thus they have no constructors.

That's where the `E` object comes into play. It allows to easily create `EShape` instances

## Style
Abstracting the `Paint` class, allowing the use of stroke and fill colours at the same time:

```

    val circleVE = E.Circle(radius = 10.dp).toVisualEntity { circle ->
        strokeColor = Color.RED
        strokeWidth = 2.dp
        fillShader = circle
            .diagonalTLBR() // creates a line from top left to bottom right
            .toShader(Color.RED, Color.YELLOW) // converts it to a linear gradient with these colours
    }.toAndroid()

    canvas.drawFromCenter {
        draw(circleVE)
    }

```
**TODO Add image**

Here we're creating a circle with a radius of 10, we give it a red stroke of 2dp.

Next, we create a diagonal ELine inside this circle and convert it to a `LinearGradient`

*This project aiming to be multiplatform, `toAndroid()` is required to actually create the paints*

##  Moving things around

### Copy
Before moving a shape, you might want to make a copy of it. `EShape<T>` has a function that returns a copy of itself `fun copy() : T`
So we have the following `interface ECircle : EShape<ECircle>`. Keep this in mind if you want to add custom shapes, it needs to be copyable.

### Self/Set functions and mutability

Any function that starts with the word `self` or `set` will mutate the object. Any other function won't  - **TODO: Update code to reflect this**

Example:

```kotlin
    val square = E.Rect.Square(100)
    square.offset(10,10) // returns a new rectangle
    square.selfOffset(10,10) // updates the current rectangle
```

### Alignment
Any shape can be aligned relative to any other shape. When moving a shape, you can either create a copy at that new location or modify one to avoid memory allocation

```
val rect = E.Rect(x = 0, y = 0, width = 100.dp, height = 100.dp)
val circle = E.Circle(radius = 10.dp)

// Creates a new circle at the top left of the rectangle, inside the rectangle
val newCircleOutside = circle.alignedOutside(rect, EAlignment.topLeft)
// Creates a new circle at the top left of the rectangle, outside the rectangle
val newCircleInside = circle.alignedOutside(rect, EAlignment.topLeft)

// Move the circle at the top left of the rectangle, inside the rectangle
circle.selfAlignInside(rect, EAlignment.topLeft)

// Move the circle at the top left of the rectangle, outside the rectangle
circle.selfAlignOutside(rect, EAlignment.topLeft)
```

**TODO add alignments images**
