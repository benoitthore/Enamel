
# Motivation
The Android Canvas API is a powerful tool, but sometimes it can be very hard to use. This usually result in a lot of boilerplate, which makes the code harder to read and debug.

Using newer language features provided by Kotlin, this library intend to bring Android's Canvas as easy to use as possible.


# Modularisation
Enamel is a set of libraries designed to facilitate the use of the Android **Canvas**. It consists of multiple modules:

- Core (Kotlin): Basic mathematical functions used by other modules.
- Geometry (Kotlin): Geometry shapes and concepts such a point, angle, rectangle, circle, etc.
- Geometry (Android): Plug for the *Geometry* module into Android, it contains extensions on Canvas related classes such as **Canvas**, **Path**, **View**.
- Touch (Android): Utility functions to handle touch event in an easy manner.
- Visual Entities (Android): Draw shapes defined using the *Geometry* module, abstracting the **Paint** class.
- Animation (Android): Animation utilities for *Visual Entity*
- Layout (Kotlin): Layout system designed to work in any environment.
- Layout Canvas (Android) (coming soon): Bridge between the *Layout* module the Android SDK

# Geometry
## Architecture
When drawing on a Canvas, you need to position shapes relative to one another. This library helps doing it by providing a set of combined interfaces, you can see the full [list of components here](TODO).
All Enamel object are extending a set of interfaces, EPoint, ECircle, etc. are actually interfaces. The reasoning behind using interfaces is that it's much easier to combine them. This will get clearer when we look at how to use the **HasBounds** interface.

## Mutability
When it comes to mutability, software engineering fundamentals tells you that immutable data structures makes your code safer as it avoids side effects. Unfortunately sometimes performances are

Each interface has a mutable version that you can use in order to position shapes without having to allocate objects.

E*\<Class\>* and E*\<Class\>*Mutable are like List and MutableList in Kotlin: in the background, the instance is always mutable. The interface used as a return type defines mutability.

When dealing with an immutable instance, any function will return a new object, thus allocating memory.

In some cases it doesn't matter, but when it does you have to options. Let's say we're dealing with a point and we want to move it. We can just call the `offset(x,y)` function, which will return a new EPoint and with the applied offset. In order to re-use a predefined EPoint, for example in a draw loop. Providing a `target =` parameter prevents the allocation, like so `offset(x,y, target = somePoint)`

*It is recommanded to always used named parameter when passing a `target` in order to make the code easier to understand*

Any mutable function will always start by the word `self`, calling the "non-self" it, such as `selfOffset(x,y)`. The implementation of self functions always follows the following pattern:
`selfFUNCTION(params) = super.FUNCTION(params, target = this)`

This way, calling a mutable function and immutable function always gives the same result.

Because self and non-self functions return objects, they can be chained like so `selfOffset(a,b).selfCcale(c,d)` or `offset(a,b).scale(c,d)`
Something to keep in mind is that each function call on a non-self function will allocate an object if a `target` isn't provided

## HasBounds/CanSetBounds


# ERectGroup