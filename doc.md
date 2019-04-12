# Motivation


The Android Operating System has been built in the early 2000s for digital cameras. Many aspects of its SDK have changed since, apps aren't made in the way they used to be, but the core of the view/layout system is still the same.

Even with ConstraintLayout's visual tool, which is easier to understand and manipulate than XML, the layout is essentially still defined in XML and there are not many aways to work around it.

When Kotlin came around, people tried to make DSLs like Anko to enabled developers to design user interfaces programmatically in a much easier way than if using the raw Java API.

Unfortunately there is many downsides using a Kotlin DSL. There is no instant preview: in order to see the result of a layout change, the app needs to be built and it can take between a few seconds to several minutes depending on the size of your project and your computer.

The Android View API is supposed to be written in XML and executed on the Android OS, not on a computer. This means that any attempt to make the view creation process easier would have to work around these limitations.

In order to fix this problem, this library has re-implements most of the layout stack in a way that allows it to be flexible.


# Goal
Create a algorithmical way of building layouts following these guidelines:
- What you see is what you get
- Kotlin friendly
- Cross platform with an Android and Desktop implementation for fast prototyping
- Support transition using animations
- Can easily be extended: focused on composition and layering the API
- Hot reload by sending a new layout to a running app/program
- Few allocations in order to be garbage collection friendly




# Layers
Computers only work with numbers, so do layout systems. In order to make a good layout system, a good geometry stack is required.

This library uses different layers in order to achieve its goals, the lowest layer being raw geometry with `points`, `rectangle`, `circles`, and other basic geometry components, as well `sizes` and `angles` for measurements  

In the `Primitve` and the `Figure` layers, every object has a **Mutable** version. Example: `EPointMutable` extends `EPoint`.

The **Mutable** class extends the immutable one which can sometimes be a source of error but it's unfortunately required in order to keep the library small and efficient when it comes to memory allocation

Most functions have an optional **buffer** parameter which can be passed in order to avoid allocations.

## Primitives
Primitives are object made of raw Java types such as numbers and boolean. Enums can also be included in this layer.

### EPoint
It's just a point: using an `x` and a `y` position

Functions can be applied to move it around according to another point, an angle, a distance, or any combination of these 3  
### ESize
ESize is a holder for `width` and `height`

### EAngle
Angles are composed of a value and an `AngleType`: degrees (from 0 to 360), radians (from 0 to 2π) and rotations (from 0 to 1). An EAngle having a value of 2 rotations being perfectly valid.

When it comes to dealing with angles, most libraries using geometry expect a decimal numbers which can sometimes be radians or degrees, this class abstracts this concept.


## Figures

### ECircle
This class is made of an `EPoint` and radius. 

It also has a few handy function, for example it can split itself into a list of `EPoint` or returns its inner/outter square 

### ERect
Probably the most important class in the entire library, the `ERect` is made of an origin `EPoint` being its top left corner and an `ESize`.

It also has a lot of handy functions. For example `pointAtAnchor(x,y)`, with x and y being between 0 and 1, will return an `EPoint` according to this values. For example `pointAtAnchor(0.5,0.5)` will return the **center** of the rectangle, `pointAtAnchor(0,1)` will return the **top right** corner. 

**RectAlignment**

`ERectEdge`: left, top, right, bottom

`EAlignment`: topLeft, topCenter, topRight, bottomLeft, bottomCenter, bottomRight, center, leftTop, leftCenter, leftBottom, rightTop, rightCenter, rightBottom

`topLeft` and `leftTop` means different things, see `EStackLayout` for clarification
    
## Layouts

The Layout layer is where thing get a bit more concrete, it might/should be extracted into a separate module/repo in the future.

Every layout in the library has to implement the `ELayout` interface which forces them to implement the following:
 
`val childLayouts: List<ELayout>` : List of child layout. Can be empty and/or mutable

`fun size(toFit: ESize): ESize`
It's the equivalent of Android's `onMeasure`, your being given and you need to return the size you actually need.

In the Android framework there is this concept of **Mode** which can be AT_MOST,EEACTLY or UNSPECIFIED. In this library this concept is abstracted thus not required. 

`fun arrange(frame: ERect)`
This function is like the `onLayout` function of Android, you're given a frame and the layout has to arrange itself into it


**TBC**: `arrange()` and `size()` don't have to obligation to respect the parameter being passed to them, but should most of the time.


### EBarLayout
 
Align a layout on a single axis (vertical or horizontal) by providing an `ERectEdge`

If a vertical axis is passed (top or bottom) the layout is going to match its parent height and wrap its content width.

The `snugged()` function from the DSL can be called in order to wrap content on both axis

### EBoxLayout

Arrange layouts in both axis (vertical and horizontal)

It's the equivalent of Android's FrameLayout, the EAlignment being its gravity.

It has a `snugged` value which basically works as a wrap_content/match_parent for both vertical and horizontal.

In order to have a different snugging value for width AND height, use an `EBarLayout` instead  

### EStackLayout

The equivalent of a LinearLayout in Android, with a bit more control on how layouts are aligned next ot each other.

For example with bottomRight it aligns its children vertically at the bottom of each other and put the whole things on the right. So **`bottomRight` != `rightBottom`**

`bottomLeft` will align children at the bottom of each other so the alignment will be done from top to bottom. Using `topLeft` will do the opposite and align children from bottom to top.  

This layout wraps content in one dimension and fill the other, can be `snugged()` in order to wrap content on both axis

For example, with a `bottomRight` value it will fill horizontally and wrap vertically 

### EPaddingLayout

A simple way to add margins: Put a layout into a EPaddingLayout and give it a top, left, right and bottom padding.

### EDivideLayout

It takes 2 layout, a `slice` and `remainder`, which will be put next to each other (vertically or horizontally)

The `spacing` parameter is how much space their should be between them

When the `snug` parameter is true, it will wrap content on both axis. Wrapping content only vertically or horizontally doesn't make sense here. 

The `division` parameter be one of the following:
- `Slice`: Give the `slice` layout as much space as it needs and give the rest to the `remainder`
- `Distance`: Give the `slice` layout a certain size and give the rest to the `remainder`
- `Fraction`: Give the `slice` layout a certain fraction of the available space and give the rest to the `remainder`

### EJustifiedLayout

This Layout works exactly like an EStackLayout apart from the fact that it's going to "justify" its children within the available space instead of snugging them all in a corner.

In this context, justify is the equivalent of the justifying function in a text editor like OpenOffice. 


### ELeafLayout
Mostly used for debugging and testing, it can take a color so the debugger will draw a rectangle of the specified color around its child. 


### ESizingLayout
This layout is used to override the size of its child in different ways: overwriting width, overwriting height, scaling, and a few more.

### ESnuggingLayout

Snugs a layout on its axis, the equivalent of Android's wrap_content feature. However it can only be used on layouts that implement `ELayoutAlongAxis`:
- EStackLayout  
- EBarLayout
- EJustifiedLayout

### ELayoutRef
TODO

### ELayoutTag
TODO


## DSL
In order to make them easier to use and compose, each layout as one or multiple functions to be created instead of calling its constructor

### EBarLayout
 `aligned(layout, ERectEdge)`

### EBoxLayout
 `arranged(EAlignment, snugged = true)` 

### EStackLayout
`List<ELayout>.stacked(EAlignment, spacing: Number = 0)`


### EPaddingLayout
`padded(left=0 top=0 right=0 bottom=0)` 
 
`padded(all=0) = padded(all all all all)`

### EDivideLayout
`aligned(ERectEdge, ELayout, EDivideLayout.Division, spacing, snugged)`

usage: `remainder.aligned(left, of = slice)`

### EJustifiedLayout
`List<ELayout>.justified(EAlignment)`

### ELeafLayout
`leaf(color)`

### ESizingLayout
`size(width, height)`

`width(value)`

`height(value)`

`scale(x,y)`

### ESnuggingLayout
`snugged()`

### ELayoutRef
TODO

### ELayoutTag
TODO


## ETransition
When going from a layout to another, the ETransition system takes care of animating the changes. The way this API is built in such a way that it can be plugged into any UI, it doesn't have to be Android. Also it only knows about the ELayout interface which mean any Layout built in the future would work with it

With ELayout, transitions work in 3 steps: out, move, in. It means views that aren't in the destination layout go away first, then the one that stays move to their new location, finally the new views appear.

One of the key benefit of ELayout is that it could be computed without committing. In other words, it's possible to call the `arrange()` from ELayout function without having to update the UI 

## Serialization
ELayouts are serialized to JSON object using the `org.json` library. `ELayoutSerializer` and `ELayoutDeserializer` know how to serialize the basic layout by default but can be easily be extended by calling `addSerializer()` and `addDeserializer()`   

## Network/Hot reload
In order to be able to send new layout to an Android device, the app is running a web server.

The following command needs to be run in order to have the Android emulator receiving network requests: `adb forward tcp:9321 tcp:9321`

Once a new ELayout has been received it is applied to the UI using a transition.  

At the moment, only 1 `EViewGroup` can be connected to the server.


## UI plug
Since ELayout is purely based on math, it can be plugged into any UI system which makes it a great tool for fast prototyping since it can run directly on the computer without having to use an emulator.

### Processing plugin
The Processing library is used in order to draw ELayout directly on a computer. It can only display `ELayoutLeaf` and is should be used to test and play with ELayout

### Android plugin
The Android plugin allows ELayout to work on Android by providing implementations to `ETransition` so it can run animations. It is also responsible for drawing Android `View` according to their `ELayoutRef` position 
