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

# DONe
## Fix EDivideLayout and EBarLayout serializations

# ONGOING
 
## ELinearLayout as an alternative solution to the confusing DSL (LinearLayout on steroids)
Use this as reference https://fblitho.com/docs/layout

## Fix example so it works like on the original Enamel 


# NEXT
## Find a solution for ELayoutRef to be instantiated with an empty constructor
## Fix android layout bug with Gravity.CENTER on TextView as a way of reproducing
## Test with more than 1 EPViewGroup on the UI
## Make transition return a transition object and use it to control the transition with a touch event as an example
## Refactoring with serialisation to use JSONObject
Refactor serializer class to something simpler and outside the ELayout layer like so:
interface ELayoutSerializer<T> {
    fun serializer(layout : ELayout) : T
    fun deserialize(data : T) : ELayout
} 


# BACKLOG
## Implement onDebugDraw 
## Extract ELayout to its own branch/repo 
## Refactor ELayout so it doesn't allocate on sizing and arranging 
## Unit tests 
## Improve Transition performance 
 

# MAYBE
## Create Web Playground (linked with Android)
## Create Web Playground (Using JS Canvas)



