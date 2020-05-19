# Tutorial

## Drawing a rectangle
*This tutorial assumes that you have a basic understanding of the Canvas API*

First of all, create a custom view and override `onDraw()`. Create a rectangle using the `E` object, the draw it with the `draw` extension on Canvas

*For more info on `E`, see the [Geometry documentation](TODO)*

```
class MyEnamelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val rectanglePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2.dp
        color = Color.RED
    }
    
    // Creates a rectangle at 0,0 with a width of 200dp and a height of 400dp
    private val rectangle = E.RectMutable(0, 0, 200.dp, 400.dp)
    
    override fun onDraw(canvas: Canvas) {
        canvas.draw(rectangle, rectanglePaint)
    }
}


...
// In your Activity
setContentView(MyEnamelView(this))
```

Nothing special here, just a red rectangle.
This rectangle is at the top left of the View because we've set its origin to `0,0`.


If we want to center it in the view, we first need to get the bounds of the parent view. This happens on layout. So let's override this function:
```
    private val frame = E.RectMutable()
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        
        // Give our variable the same bounds as the View
        frame.setBounds(this)

        // align the rectangle inside
        rectangle.selfAlignInside(frame, EAlignment.center)
    }
```

Here we are creating our `frame` rectangle outside onLayout, in order to avoid allocating an object during `onLayout()`.

Then we call `selfAlignInside` on our `rectangle` to align it in the center of the frame.

You can use any other value from the `EAlignment` enum to align the rectangle in a different orientation.


## Drawing more shapes
Drawing from a frame is usually better than using absolute pixel coordinate. In this section, we will see how to create shapes from our rectangle and draw them on screen. For simplicity, we won't be avoiding allocation onDraw here.

In order to visualise the relation between the shapes we're drawing, let's create a second paint:

```
private val shapePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4.dp
        color = Color.GREEN
    }
```

## Rectangle to Line
The easiest line to get from a rectangle are its diagonals, we can do it with a simple function call like so:

```
override fun onDraw(canvas: Canvas) {
        //draw the rectangle
        canvas.draw(rectangle, rectanglePaint)
       

        //get the diagonals
        val topLeftBottomRightDiagonal = rectangle.diagonalTLBR()
        val topRightBottomLeftDiagonal = rectangle.diagonalTRBL()

        //draw the circles
        canvas.draw(topLeftBottomRightDiagonal, shapePaint)
        canvas.draw(topRightBottomLeftDiagonal, shapePaint)
    }
```


## Rectangle to circle
From a rectangle, we can get its inscribed circle and its circumscribed circle, by calling the `outerCircle` and `innerCircle` functions like so:

```
 override fun onDraw(canvas: Canvas) {
        //draw the rectangle
        canvas.draw(rectangle,rectanglePaint)

        //get the circles
        val inscribed = rectangle.innerCircle()
        val circumscribed = rectangle.outerCircle()

        //draw the circles
        canvas.draw(inscribed, shapePaint)
        canvas.draw(circumscribed, shapePaint)
    }
```

## Circle to point
From a circle, we can get a list of equally spaced points around its circumference.
We can use this in many ways, here's an example of drawing smaller circles around this original circle:


```
 override fun onDraw(canvas: Canvas) {
        //get the circle
        val circle = rectangle.innerCircle()
        canvas.draw(circle, rectanglePaint)

        val circleList = circle
            // gets 5 points equally spaced around the circle
            .toListOfPoint(5)
            // convert these points to circles, with an 8dp radius
            .map { point -> point.toCircle(8.dp) }

        // Draw the circle list
        canvas.drawCircleList(circleList, shapePaint)
    }
```

## Etc.
There are many other ways to create shapes, check the full list [here](TODO)