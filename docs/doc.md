
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

Because everything is an interface and doesn't have a direct constructor, the `E` object is provided to create almost any interface provided by this library.

## Mutability
### Why is mutability important?
When it comes to mutability, software engineering fundamentals tells us that immutable data makes code safer as it avoids side effects. Unfortunately sometimes performances are too important to have the luxury of immutability, since every mutation requires the allocation of a new object.

In Enamel, each shape interface has a mutable version that can be used to position shapes without having to allocate more memory.

E*\<Class\>* and E*\<Class\>*Mutable are like Kotlin's `List` and `MutableList`. However, when creating an immutable instance through the `E` object, you will effectively get a mutable instance, but it will be **upcasted** to be immutable.

When dealing with an immutable instance, mutation functions return a new object, thus allocating memory. For example, `point.offset(x,y)` will return a new EPoint offsetted by that amount.

In some cases, this doesn't matter, but when it does, it's good to have the option

### How to create a mutable object?

Just like immutable objects, mutable objects can be created using the `E` object.

Alternatively, any function that returning geometry object returns it in its mutable form, which can the be upcasted to its immutable form

### How to use it?

Let's say we're dealing with a point and we want to move it. We can just call the `offset(x,y)` function, which will return a new EPoint with the applied offset. If we want to prevent the allocation from happening, for example in a draw loop, we can re-use a predefined EPoint. Providing a `target =` parameter prevents the allocation, like so `offset(x,y, target = somePoint)`

*It is recommanded to always use named parameter when passing a `target` in order to make the code easier to understand*

Any mutable function will always start by the word `self`, calling its "non-self". The implementation of self functions always follows the following pattern:
`selfFUNCTION(params) = super.FUNCTION(params, target = this)`

This way, calling a mutable function and immutable function always gives the same final result.

Because self and non-self functions return objects, they can be chained like so `selfOffset(a,b).selfCcale(c,d)` or `offset(a,b).scale(c,d)`
Something to keep in mind is that each call of a non-self function will allocate an object if the `target` parameter isn't provided.

## HasBounds/CanSetBounds
Earlier we saw that geometry objects extend a set of interfaces, the most common one being `HasBounds`, or in its mutable form: `CanSetBounds`

`HasBounds` only needs 4 `vals`: left, top, right and bottom

`CanSetBounds` extends `HasBounds` by adding `setBounds(left,top,right,bottom)`

Through a set of extension functions, an following the "self/non-self" pattern previously mentionned, this interface is particulary useful to move objects arround

You can find the full list of functions [here](TODO)

# EAngle

# ERectGroup