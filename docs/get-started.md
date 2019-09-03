# Setup Environment
## Get the code

This library is in its early alpha version, it will be published later so it can be imported using Gradle.
For now, in order to use it you'll have to clone the repo:

`git clone https://github.com/benoitthore/Enamel`

## Hello world

Open the `MainActivity.kt` file under the `layout-playground-android` module.

`helloWorld(this)` returns an `EViewGroup` which is the main ViewGroup from this library, it allows you to display predefined `ELayout`.

Here is what it looks like:
```Kotlin
private val TITLE = "title"
private val SUBTITLE = "subtitle"

fun helloWorld(context: Context): EViewGroup = context.eViewGroup {

      
    // Set the background color of the entire ViewGroup
    backgroundColor = Color.LTGRAY

    val title = context
        .textView {
            // define TextView
            text = "Title"
            textSize = 20f
            setTextColor(Color.WHITE)
            backgroundColor = Color.RED
        }
        .withTag(TITLE) // Add a transition tag
        .laid() // create layout reference

    val subTitle = context
        .textView {
            // define TextView
            setTextColor(Color.WHITE)
            textSize = 18f
            backgroundColor = Color.BLUE
            text = "Sub Title"
        }
        .laid() // create layout reference
        .withTag(SUBTITLE) // Add a transition tag

    listOf(title, subTitle)
        .stacked(bottomRight, 16.dp) // Stack subTitle on the bottom right corner of title
        .arranged(topRight) // Put to whole layout on the topLeft
}
```

Let's go through it step by step. TITLE and SUBTITLE can be ignored for now, they're used for transition.

The first 2 statements create 2 TextViews using Splitties' DSL. However, these are Views and can't directly be used in the ELayout environment. They are converted into ELayout instances using the `laid()`. We will see what `withTag(TAG)` does later when we look at the transition API.


The last line is where the layout is actually created:
```Kotlin

    listOf(title, subTitle)
        .stacked(bottomRight, 16.dp) // Stack subTitle on the bottom right corner of title
        .arranged(topRight) // Put to whole layout on the topLeft
```

This means _Take title and subtitle, stack them at the bottom right of each other with a spacing of 16dp and put the whole thing on the top right corner_

If you've used the native Android layout system, the `stacked()` function is like a `LinearLayout`. `bottomRight` means that each layout is placed on the bottom right corner of the previous one.

If the previous paragraph doesn't make sense, try using instead `bottomLeft`, `topRight`, etc.

`arranged(topRight)` is the equivalent of a FrameLayout with a top right gravity.

_If you write `arranged(topLeft)` the layout will stay on the top right, this is because by default an EStackLayout is "match_parent" by default. This can be fixed like so `.stacked(bottomRight, 16.dp).snugged().arranged(topLeft)`_



## Transition
In order to load the transition example, modify your MainActivity like so `setContentView(helloWorldTransition(this))` and run the app.

When clicking anywhere on the screen, you should see a transition happening.

Now let's look at the code inside `helloWorldTransition()`.

```Kotlin
    val viewGroup = helloWorld(context)

    // Get the layout tags
    val title = TITLE.layoutTag
    val subTitle = SUBTITLE.layoutTag

    // Define second layout
    val newLayout = listOf(title, subTitle)
        .stacked(bottomRight, 16.dp).snugged()        // Snug means wrap_content (horizontally in this case)
        .arranged(topLeft)
        .padded(16.dp)


    // Transition logic
    var defaultLayout = true

    val oldLayout = viewGroup.layout

    viewGroup.setOnClickListener {
        if (viewGroup.isInTransition) {
            Toast.makeText(context, "Already in transition", Toast.LENGTH_SHORT).show()
            return@setOnClickListener
        }
        if (defaultLayout) {
            viewGroup.transitionTo(newLayout)
        } else {
            viewGroup.transitionTo(oldLayout)
        }

        defaultLayout = !defaultLayout
    }

    return viewGroup
```

On the first line, we're getting the view from the previous example. Then we create 2 layout tags that we will use to reference the 2 TextViews in the rest of the code.

Then `newLayout` is created. The rest is pretty self-explanatory: swap from one layout to the other every time the view is clicked.


### Using KTS
Now that we're created a layout, we can modify it at runtime using a KTS file.

In order to make this possible, we need to start a server on the device that will listen for changes. Simply call `startServer()` on the EViewGroup we've created in the previous example:

```Kotlin
fun helloWorldTransition(context: Context): EViewGroup {

    val viewGroup = helloWorld(context)
    viewGroup.startServer()
   
    ...
```

At this point, the app is listening but if using an Emulator, you'll need to forward the traffic, so the app's server can get the data. You can do so by running the following command: `adb forward tcp:9321 tcp:9321`

If the view is listening and the port forwarding is enabled, you should be able to see it on this page: http://localhost:9321/test

Now that the app is listening for changes, create a Kotlin scratch file (`CMD`+`SHIFT`+`N`on Mac OS) then paste this code:

```Kotlin
import com.benoitthore.enamel.geometry.layout.dsl.*
import com.benoitthore.enamel.geometry.alignement.EAlignment.*
import com.benoitthore.enamel.geometry.layout.playground.PlaygroundClient
import com.benoitthore.enamel.geometry.layout.playground.sendToPlayground

// Replace localhost by an IP address if you're using a physical device
PlaygroundClient.defaultClient = PlaygroundClient("localhost")

val Number.dp get() = toFloat() * 3

val title = "title".layoutTag
val subTitle = "subtitle".layoutTag

listOf(title, subTitle)
    .stacked(bottomRight, 16.dp)
    .snugged()
    .arranged(bottomRight)
    .padded(16.dp)
// Send layout to the device
    .sendToPlayground()
```

_Make sure you select the `ProcessingTest` module in the toolbar_

You can now run the code by pressing the Play button or `CMD`+`SHIFT`+`W` on Mac OS.

If everything has been set up properly, you should see the views being updated.

From now, you can play with the layout and try different possibilities.


# MORE COMMING
....