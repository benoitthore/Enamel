package com.benoitthore.enamel.core.math.noise

import com.benoitthore.enamel.core.math.d

operator fun OpenSimplexNoise.invoke(x: Number) = eval(x.d)
operator fun OpenSimplexNoise.invoke(x: Number, y: Number) = eval(x.d, y.d)
operator fun OpenSimplexNoise.invoke(x: Number, y: Number, z: Number) = eval(x.d, y.d, z.d)
