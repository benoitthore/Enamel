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

# ONGOING
## Refactoring with serialisation to use JSONObject
---- Add missing implementation
- Playground Server/Client
- Android Leaf impl
- Make 1 object that contains both implementation of serializer and deserializer for easier maintainability   
## Fix example so it works like on the original Enamel 


# NEXT
## ELinearLayout as an alternative solution to the confusing DSL (LinearLayout on steroids), start with API 
Use this as reference https://fblitho.com/docs/layout
## Find a solution for ELayoutRef to be instantiated with an empty constructor (is it still valid ?)
## Fix android layout bug with Gravity.CENTER on TextView as a way of reproducing
## Test with more than 1 EPViewGroup on the UI
## Make transition return a transition object and use it to control the transition with a touch event as an example


# BACKLOG
## Implement onDebugDraw ( Is it needed with leaf() ? ) 
## Extract ELayout to its own branch/repo 
## Refactor ELayout so it doesn't allocate on sizing and arranging 
## Unit tests 
## Improve Transition performance 
 

# MAYBE
## Add clip rect
## Create Web Playground (linked with Android)
## Create Web Playground (Using JS Canvas)



