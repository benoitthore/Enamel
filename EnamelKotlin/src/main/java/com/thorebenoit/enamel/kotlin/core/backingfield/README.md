# Backing fields for Kotlin extension variables (Work In Progress)

The ExtraValueHolder delegates allows backing fields on classes without having to extend them by storing keys and values in a map with WeakReferences so it doesn't create memory leaks.

It can be used like so:
```Kotlin
  var SomeClass : String by ExtraValueHolder { "DefaultValue" }
```


Limitations :

- This does not work on primitive types 
-  Not as performant as a extending the class and adding an extra property