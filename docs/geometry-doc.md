# Geometry module

## Description

## List

### Primitives
Primitives are objects made of raw Java types such as numbers and boolean. Enums can also be included in this layer.

#### EPoint
It's just a point: using an `x` and a `y` position.

Functions can be applied to move it around relative to another point, angle, distance, or any combination of these 3.
#### ESize
ESize is a holder for `width` and `height`.

#### EAngle
Angles are composed of a value and an `AngleType`: degrees (from 0 to 360), radians (from 0 to 2π) and rotations (from 0 to 1). An EAngle having a value of 2 rotations being perfectly valid.

When it comes to dealing with angles, most libraries using geometry expect a decimal numbers which can sometimes be radians or degrees, this class abstracts this concept.

### Figures

#### ECircle
This class is made of an `EPoint` and a radius.

It also has a few handy functions, for example it can split itself into a list of `EPoint` or returns its inner/outer square.

#### ERect
Probably the most important class in the entire library, the `ERect` is made of an origin `EPoint` being its top left corner and an `ESize`.

It also has a lot of handy functions. For example `pointAtAnchor(x,y)`, with x and y being between 0 and 1, will return an `EPoint` according to this values. For example `pointAtAnchor(0.5,0.5)` will return the **center** of the rectangle, `pointAtAnchor(0,1)` will return the **top right** corner. 

**RectAlignment**

`ERectEdge`: left, top, right, bottom

`EAlignment`: topLeft, topCenter, topRight, bottomLeft, bottomCenter, bottomRight, center, leftTop, leftCenter, leftBottom, rightTop, rightCenter, rightBottom

`topLeft` and `leftTop` means different things: `TODO add GIF`
