# Roadmap to Alpha release
## Refactoring
...
## Tasks
Sorted by priority order:
- Transition API
- Documentation
- Gradle modularisation
- Unit Testing
- Code cleanup

# Post Alpha
## Refactoring:
- ELayout size and arrange functions shouldn't allocate
- ELayouts should be mutable to allow easier animations
- De/Serialization should be done by the ELayouts themselves
- Refactor EALignement to a simple enum
- Simplify calling EAlignement, ERectEdge, etc. using a dsl

## New features
- Android Canvas API link with Enamel Geometry module





////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

# DONE
## Make ELayout childLayouts mutable internally only
## Move ELayoutSerializer implementation into ELayout (ELayout should have a default empty constructor and de/serialize methods)
## Split ELayoutDataStore into Parser/Writer
## Make Parser/Writer interfaces
## Provide implementations for storing using List<Number> 
## Refactor Playground to work with new serialization
## Refactor EPView so they work like Android ViewGroup, using EDroidLayout as an example
## Implement EPView playground
## Add ETransition to EPView playground
## Implement Android Playground



# ONGOING
## Refactor serialisation to use JSONObject 
## Fix EDivideLayout and EBarLayout serializations 

# NEXT
## Fix IP Address bug on emulator
## Fix android TV bug with Gravity.CENTER
## Test with more than 1 EPViewGroup on the UI

# BACKLOG
## Improve Transition performance 
## Make transition return a transition object and use it to control the transition with a touch event as an example
## Extract ELayout to its own branch/repo 
## Refactor ELayout so it doesn't allocate on sizing and arranging 
 

# MAYBE
## Create Web Playground (linked with Android)
## Create Web Playground (Using JS Canvas)



