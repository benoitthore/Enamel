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

# Ongoing

## DONE Make ELayout childLayouts mutable internally only
## DONE Move ELayoutSerializer implementation into ELayout (ELayout should have a default empty constructor and de/serialize methods)
## Split ELayoutDataStore into Parser/Writer
## Make Parser/Writer interfaces
## Provide implementations for storing using List<Number> and JSON (debug & prod)

# NEXT
## ELayoutRef de/serialization
Interface should be provided at the ELayoutDataStore(TBC) level

## Refactor EPView so they work like Android ViewGroup
## Refactor Playground to work with new serialization
## Implement Android Playground


## Create Web Playground (linked with Android)
## Create Web Playground (Using JS Canvas)



