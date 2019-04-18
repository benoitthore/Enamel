# ELayout
## Motivation

Android’s layouts are thought to be built via XML, especially ConstraintLayout which has a really nice visual tools that comes with it. However building views this way has a few downsides, which is why people sometimes uses Kotlin DSLs to build views in Android.

The issue with these DSL is that they are nothing more than a layer on top of the raw Java APIs. Therefore, they can be a bit tricky to manipulate.

Also, when building views in code the preview isn’t available straight away. You need to first build your project which can take time if you need to do it many times in a row.

The goal of this library is to provide an alternative layout system that could fix some of these issues and make UI code more re-usable.


This library is essentially a **ViewGroup**, it doesn't have any DSL to build views so you can use it with whatever you prefer. However I recommend to use it with [Splitties View DSL](https://github.com/LouisCAD/Splitties) because this one just creates views without adding them to a ViewGroup   

## Goal
Create a algorithmical way of building layouts following these guidelines:
- What you see is what you get
- Kotlin friendly
- Cross platform with an Android and Desktop implementation for fast prototyping
- Support for animated transition
- Can easily be extended: focused on composition and layering the API
- Hot reload by sending a new layout to a running app/program
- Few allocations in order to be garbage collection friendly




# Credits
https://github.com/screensailor
