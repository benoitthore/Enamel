# ELayout
## Motivation

Android's layout system is design to be used with XML, even though Kotlin DSLs exist, it's sometimes hard to work with and the lack of preview doesn't help.

This is an attempt to create an easier way of building UI using Kotlin.  

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


[Get started](https://github.com/benoitthore/Enamel/wiki/Get-started) 



# Credits
https://github.com/screensailor
