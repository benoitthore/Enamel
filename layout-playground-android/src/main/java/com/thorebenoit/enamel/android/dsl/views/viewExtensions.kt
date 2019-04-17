package com.thorebenoit.enamel.android.dsl.views

import android.view.View
import android.widget.TextView
import java.lang.Exception


var View.backgroundColor: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
    set(value) {
        setBackgroundColor(value)
    }
var View.padding: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
    set(value) {
        setPadding(value, value, value, value)
    }
var TextView.textColor: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
    set(value) {
        setTextColor(value)
    }

// Usage @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
const val NO_GETTER: String = "Property does not have a getter"

fun noGetter(): Nothing = throw Exception("Property does not have a getter")


