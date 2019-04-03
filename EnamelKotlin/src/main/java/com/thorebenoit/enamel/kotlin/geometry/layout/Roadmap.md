# Roadmap to Alpha release

## Tasks
Sorted by priority order:
- Documentation
- Gradle modularisation
- Unit Testing
- Code cleanup


## New features
- Android Canvas API link with Enamel Geometry module


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

# DONE
## Fix EDivideLayout and EBarLayout serializations
## Find a solution for ELayoutRef to be instantiated with an empty constructor (is it still valid ?)
## Refactoring with serialisation to use JSONObject
---- Add missing implementation
---- Playground Server/Client
---- Android Leaf impl


# ONGOING
## Align Weight, Linear, Frame(TODO) 
## Add copy and equal functions 
## Replace Snugging with boolean /// OR replace snugged by wrap()
## Android friendly implementation:
When removing the snugging bit in order to make it closer to Android's API, dupplicate appears like arranged/aligned.
JustifyLayout is still using snugging
SnuggingLayout isn't needed anymore


## Bug Fix
### Fix example so it works like on the original Enamel 
### Fix Scale in ESizingLayout 


# NEXT
## Start documentation

## ELinearLayout as an alternative solution to the confusing DSL (LinearLayout on steroids), start with API 
Use this as reference https://fblitho.com/docs/layout

## Fix android layout bug with Gravity.CENTER on TextView as a way of reproducing
## Test with more than 1 EPViewGroup on the UI
## Make transition return a transition object and use it to control the transition with a touch event as an example

## Extract ELayout to its own branch/repo 

# Post 0.1.0
## Refactor ELayout so it doesn't allocate on sizing and arranging 
## Unit tests 
## Improve Transition performance 
 

# MAYBE
## Start End instead of Left,Right
## Add clip rect
## Create Web Playground (linked with Android)
## Create Web Playground (Using JS Canvas)



