## Backing fields for Kotlin extension variables (Work In Progress)

The ExtraValueHolder delegates allows backing fields on classes without having to extend them by storing keys and values in a map with WeakReferences so it doesn't create memory leaks.

When creating an ExtraValueHolder, a coroutine is started and waits until the Garbage Collector kicks in. When this happens the map is cleared so it only keeps WeakReferences that are still holding on to a value.

It can be used like so:
```Kotlin
  var SomeClass : String by ExtraValueHolder { "DefaultValue" }
```


Limitations :

- This does not work on primitive types 
-  Not as performant as a extending the class and adding an extra property
