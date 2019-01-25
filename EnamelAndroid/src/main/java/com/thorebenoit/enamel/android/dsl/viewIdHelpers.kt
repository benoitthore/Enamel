package com.thorebenoit.enamel.android.dsl

import android.view.View
import androidx.core.view.ViewCompat

fun <T : View> T.withID(): T = apply {
    if (id == View.NO_ID) {
        id = ViewCompat.generateViewId()
    }
}

