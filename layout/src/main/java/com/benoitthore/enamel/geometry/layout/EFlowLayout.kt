package com.benoitthore.enamel.geometry.layout

import com.benoitthore.enamel.geometry.alignement.EAlignment
import com.benoitthore.enamel.geometry.alignement.EAlignment.bottomLeft
import com.benoitthore.enamel.geometry.alignement.EAlignment.rightCenter
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.size.ESize
import com.benoitthore.enamel.geometry.layout.dsl.snugged

fun Iterable<ELayout>.flowed(
    childSpacing: Number = 0,
    childAlignment: EAlignment = rightCenter,
    lineSpacing: Number = 0,
    lineAlignment: EAlignment = bottomLeft
) = EFlowLayout(
    children = this.toList(),
    childSpacing = childSpacing,
    childAlignment = childAlignment,
    lineSpacing = lineSpacing,
    lineAlignment = lineAlignment
)

class EFlowLayout(
    override val children: List<ELayout>,
    val childSpacing: Number = 0,
    val childAlignment: EAlignment = rightCenter,
    val lineSpacing: Number = 0,
    val lineAlignment: EAlignment = bottomLeft
) : ELayout {
    override fun size(toFit: ESize): ESize =
        measurableArrange(rect = E.Rect(size = toFit), shouldArrange = false)

    override fun arrange(frame: ERect) {
        measurableArrange(frame)
    }

    private fun measurableArrange(rect: ERect, shouldArrange: Boolean = true): ESize {
        var rowWidth = 0f
        val cols = mutableListOf<ELayout>()
        val row = mutableListOf<ELayout>()
        val rowSize = E.SizeMutable()
        val limit = if (childAlignment.isVertical) rect.height else rect.width

        children.forEachIndexed { i, child ->
            row += child

            rowSize.set(
                EStackLayout(spacing = childSpacing, alignment = childAlignment, children = row)
                    .size(rect.size)
            )
            rowWidth = if (childAlignment.isVertical) rowSize.height else rowSize.width

            if (rowWidth > limit) {
                cols += EStackLayout(
                    spacing = childSpacing,
                    alignment = childAlignment,
                    children = row
                        .toMutableList()
                        .apply { if (isNotEmpty()) removeAt(row.size - 1) }
                ).snugged()
                row.lastOrNull()?.let { row.clear(); row += it }
            }

            if (i == children.size - 1) {
                cols += EStackLayout(
                    spacing = childSpacing,
                    alignment = childAlignment,
                    children = row.toMutableList()
                ).snugged()
            }
        }
        val stack = EStackLayout(
            spacing = lineSpacing,
            alignment = lineAlignment,
            children = cols
        ).snugged()

        if (shouldArrange) {
            stack.arrange(rect)
        }

        return stack.size(rect.size)
    }
}
