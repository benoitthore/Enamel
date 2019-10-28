package com.benoitthore.enamel.layout.android

import android.content.res.Resources

inline val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density).toInt()



