# Geometry module

## Description

This module defines basic geometric shapes and a set of functions to manipulate them.

Because this module is mainly built using interfaces and most classes are effectively interfaces, they don't have a constructor. In the following list, **Builders** represent the function to call on the `E` object or `IE` interface in order to build the object

Most objects can return other object, for example, a retangle can return 4 points corresponding to its cornes.

**TODO Talk about mutability here**

## List

### Primitives
Primitives are objects made of raw Java types such as numbers and boolean. Enums can also be included in this layer.

#### EPoint
It's just a point: using an `x` and a `y` position.

Functions can be applied to move it around relative to another point, angle, distance, or any combination of these 3.

**Builders:**

- `Point(x,y)` : creates a point at coordinate x,y
- `Point(angle,magnitude)` : Creates a point at a the specified angle and at the specified distance of `0,0`

**Modifiers**

TODO

**Converts to**

- ECircle - `toCircle(radius)`: creates a circle with this point as the center, with the given radius


#### ESize
ESize is a holder for `width` and `height`.

**Builders:**

TODO

**Modifiers**

TODO

**Converts to**

TODO


#### EAngle
Angles are composed of a value and an `AngleType`: degrees (from 0 to 360), radians (from 0 to 2π) and rotations (from 0 to 1). An EAngle having a value of 2 rotations being perfectly valid.

When it comes to dealing with angles, most libraries using geometry expect a decimal numbers which can sometimes be radians or degrees, this class abstracts this concept.

**Builders:**

TODO

**Modifiers**

TODO

**Converts to**

TODO

### Figures

#### ECircle
This class is made of an `EPoint` and a radius.

It also has a few handy functions, for example it can split itself into a list of `EPoint` or returns its inner/outer square.

**Builders:**

TODO

**Modifiers**

TODO

**Converts to**

TODO


#### Oval
**Builders:**

TODO

**Modifiers**

TODO

**Converts to**

TODO

#### Line
**Builders:**

TODO

**Modifiers**

TODO

**Converts to**

TODO


#### ERect / HasBounds / CanSetBounds
**Builders:**

TODO

**Modifiers**

TODO

**Converts to**

TODO


**RectAlignment**

`ERectEdge`: left, top, right, bottom

`EAlignment`: topLeft, topCenter, topRight, bottomLeft, bottomCenter, bottomRight, center, leftTop, leftCenter, leftBottom, rightTop, rightCenter, rightBottom

`topLeft` and `leftTop` means different things: `TODO add GIF`
