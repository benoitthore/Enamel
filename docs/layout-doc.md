## Layouts

The Layout layer is where thing get a bit more concrete, it might/should be extracted into a separate module/repo in the future.

Every layout in the library has to implement the `ELayout` interface which forces them to implement the following:

`val childLayouts: List<ELayout>` : List of child layouts. Can be empty and/or mutable

//TODO: what does being given meant/size you actually need?

`fun size(toFit: ESize): ESize`
It's the equivalent of Android's `onMeasure()`, you're being given and you need to return the size you actually need.

In the Android framework, there is this concept of **Mode** which can be AT_MOST, EXACTLY or UNSPECIFIED. In this library, this concept is abstracted and thus not required.

`fun arrange(frame: ERect)`
This function is like the `onLayout` function of Android, you're given a frame and the layout has to arrange itself into it

//TODO: It's TBC so I guess it's not super important but you could have particular layouts ignore the frame and size?

**TBC**: `arrange()` and `size()` don't have an obligation to respect the parameter being passed to them, but should most of the time.


### EBarLayout

Align a layout on a single axis (vertical or horizontal) by providing an `ERectEdge`.

If a vertical axis is passed (top or bottom) the layout is going to match its parent height and wrap its content width.

The `snugged()` function from the DSL can be called in order to wrap content on both axis.

### EBoxLayout

Arrange layouts in both axis (vertical and horizontal).

It's the equivalent of Android's FrameLayout, the EAlignment being its gravity.

It has a `snugged` value which basically works as a wrap_content/match_parent for both vertical and horizontal.

In order to have a different snugging value for width AND height, use an `EBarLayout` instead.

### EStackLayout

The equivalent of a LinearLayout in Android, with a bit more control over how layouts are aligned next to each other.

For example, with `bottomRight` it aligns its children vertically at the bottom of each other and put the whole thing on the right. So **`bottomRight` != `rightBottom`**

`bottomLeft` will align children at the bottom of each other so the alignment will be done from top to bottom. Using `topLeft` will do the opposite and align children from bottom to top.

This layout wraps content in one dimension and fills the other, can be `snugged()` in order to wrap content on both axis.

For example, with a `bottomRight` value it will fill horizontally and wrap vertically.

### EPaddingLayout

A simple way to add margins: Put a layout into a EPaddingLayout and give it a top, left, right and bottom padding.

### EDivideLayout

It takes 2 layouts, a `slice` and `remainder`, which will be put next to each other (vertically or horizontally).

The `spacing` parameter is how much space their should be between them.

When the `snug` parameter is true, it will wrap content on both axis. Wrapping content only vertically or horizontally doesn't make sense here.

The `division` parameter be one of the following:
- `Slice`: Give the `slice` layout as much space as it needs and give the rest to the `remainder`
- `Distance`: Give the `slice` layout a certain size and give the rest to the `remainder`
- `Fraction`: Give the `slice` layout a certain fraction of the available space and give the rest to the `remainder`

### EJustifiedLayout

This Layout works exactly like an EStackLayout except that it "justifies" its children within the available space instead of snugging them all in a corner.

In this context, justify is the equivalent of the justifying function in a text editor like OpenOffice.


### ELeafLayout
Mostly used for debugging and testing, it can take a color so the debugger will draw a rectangle of the specified color around its child.


### ESizingLayout
This layout is used to override the size of its child in different ways: width, height, scaling, and a few more.

//TODO - It's sorta hard but it might worth coming up with a new word instead of snug. Doesn't work here
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
In order to make them easier to use and compose, each layout can be created with one or multiple functions instead of calling its constructor.

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

