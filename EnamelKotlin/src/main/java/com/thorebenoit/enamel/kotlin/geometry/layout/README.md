# Motivation


The Android Operating System has been built in the early 2000s for digital cameras. Many aspects of its SDK have changed since, apps aren't made in the way they used to be, but the core of the view/layout system is still the same.

Even with ConstraintLayout's visual tool, which is easier to understand and manipulate than XML, the layout is essentially still defined in XML and they're not many aways to work around it.

When Kotlin came around, people tried to make DSLs like Anko to enabled developers to design user interfaces programmatically in a much easier way than if using the raw Java API.

Unfortunately there is many downsides using a Kotlin DSL. There is no instant preview: in order to see the result of a layout change, the app needs to be built and it can take between a few seconds to several minutes depending on the size of your project and your computer.

Another issue and probably the most important one, is that LinearLayout and FrameLayout have been developed with the intent of being built from XML. ContraintLayout is mostly thought to be used with the visual editor in Android Studio.

The Android View API is supposed to be written in XML and executed on the Android OS, this means that any attempt to make the view creation process easier would have to work around these limitations.

In order to fix this problem, a library would have to re-implement the whole layout stack in a way that allows it to be flexible.


# Goal
Create a algorithmical way of building layouts following these guidelines:
- Cross platform with an Android and Desktop implementation
- Supports transition using animations
- Can easily be extended (focused on composition and layering the API)
- Hot reload (by sending a new layout and applying transition)


# Layers

## Primitives

## Figures


*TODO pointAtAnchor*

## Layouts
**EBarLayout**

`aligned()` Align layouts on a single axis

**EBoxLayout**

`arranged()` Arrange layouts in both dimensions

**EStackLayout**

Snugs in one dimension and fill the other

**EDivideLayout.kt**


**EJustifiedLayout**

**ELayout.kt**

**EPaddingLayout**

**EPlaceHolderLayout**

**ESizingLayout**

**ESnuggingLayout**
