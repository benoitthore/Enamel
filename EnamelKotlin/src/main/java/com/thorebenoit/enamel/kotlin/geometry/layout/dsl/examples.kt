package com.thorebenoit.enamel.kotlin.geometry.layout.dsl

/*
     val redLayout = EPlaceHolderLayout(red)
                val blueLayout = EPlaceHolderLayout(blue)

                val sliceLayout = redLayout.sizedSquare(100)
                    .aligned(ERectEdge.top)

                val combinedLayout = sliceLayout.aligned(ERectEdge.left, of = blueLayout, spacing = 5)

                combinedLayout
                    .scaled(0.5, 0.25)
                    .padded(10)
                    .arranged(EAlignment.leftCenter)
                    .arrange(eframe)

//                combinedLayout.draw()
                redLayout.draw()
                blueLayout.draw()

 */


/*
    val views = 3.of { EPlaceHolderLayout() }

                views.mapIndexed { i, layout ->
                    layout.sizedSquare((i + 1) * 100)
                }
                    .stacked(EAlignment.rightTop, spacing = 10)
                    .snugged()
                    .arranged(EAlignment.topLeft)
                    .padded(20)
                    .arrangeAndDraw()


 */