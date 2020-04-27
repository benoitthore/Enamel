package com.benoitthore.enamel.geometry.alignement

import com.benoitthore.enamel.geometry.e.E
import com.benoitthore.enamel.geometry.primitives.EPoint

/* UI TEST
PlaygroundApplet.start(800, 800) {

        onDraw {
            background(255)
            noFill()
            stroke(red)
            val rect = eframe.inset(100).draw()
            EAlignment.all.forEach {
                rect.rectAlignedOutside(it, ESize.square(20), spacing = 10).draw()
            }

        }

    }
 */
private val spacingSignTop = E.point(0, 1)
private val spacingSignBottom = E.point(0, -1)
private val spacingSignLeft = E.point(1, 0)
private val spacingSignRight = E.point(-1, 0)
private val spacingSignCenter = E.point(0, 0)

enum class EAlignment {
    topLeft,
    topCenter,
    topRight,
    bottomLeft,
    bottomCenter,
    bottomRight,
    center,
    leftTop,
    leftCenter,
    leftBottom,
    rightTop,
    rightCenter,
    rightBottom;

    companion object {

        val all = listOf(
            topLeft,
            topCenter,
            topRight,
            bottomLeft,
            bottomCenter,
            bottomRight,
            center,
            leftTop,
            leftCenter,
            leftBottom,
            rightTop,
            rightCenter,
            rightBottom
        )
    }

    val isTop: Boolean
        get() =
            this == topLeft || this == topCenter || this == topRight ||
                    this == leftTop || this == rightTop


    val isBottom: Boolean
        get() =
            this == bottomLeft || this == bottomCenter || this == bottomRight ||
                    this == leftBottom || this == rightBottom

    val isLeft: Boolean
        get() =
            this == leftTop || this == leftCenter || this == leftBottom ||
                    this == topLeft || this == bottomLeft

    val isRight: Boolean
        get() =
            this == rightTop || this == rightCenter || this == rightBottom ||
                    this == topRight || this == bottomRight

    val isCenter: Boolean get() = this == center

    val namedPoint: EPoint
        get() = when (this) {
            topCenter -> NamedPoint.topCenter
            topLeft -> NamedPoint.topLeft
            topRight -> NamedPoint.topRight
            bottomLeft -> NamedPoint.bottomLeft
            bottomCenter -> NamedPoint.bottomCenter
            bottomRight -> NamedPoint.bottomRight
            center -> NamedPoint.center
            leftTop -> NamedPoint.topLeft
            leftCenter -> NamedPoint.middleLeft
            leftBottom -> NamedPoint.bottomLeft
            rightTop -> NamedPoint.topRight
            rightCenter -> NamedPoint.middleRight
            rightBottom -> NamedPoint.bottomRight
        }

    val isVertical: Boolean
        get() = when (this) {
            topLeft -> true
            topCenter -> true
            topRight -> true
            bottomLeft -> true
            bottomCenter -> true
            bottomRight -> true
            center -> true
            else -> {
                false
            }
        }

    val isHorizontal: Boolean
        get() = this == center || !isVertical

    val spacingSign: EPoint
        get() = when (this) {
            topLeft -> spacingSignTop
            topCenter -> spacingSignTop
            topRight -> spacingSignTop
            bottomLeft -> spacingSignBottom
            bottomCenter -> spacingSignBottom
            bottomRight -> spacingSignBottom
            center -> spacingSignCenter
            leftTop -> spacingSignLeft
            leftCenter -> spacingSignLeft
            leftBottom -> spacingSignLeft
            rightTop -> spacingSignRight
            rightCenter -> spacingSignRight
            rightBottom -> spacingSignRight
        }

    val flipped: EAlignment
        get() = when (this) {
            topLeft -> bottomLeft
            topCenter -> bottomCenter
            topRight -> bottomRight
            bottomLeft -> topLeft
            bottomCenter -> topCenter
            bottomRight -> topRight
            center -> center
            leftTop -> rightTop
            leftCenter -> rightCenter
            leftBottom -> rightBottom
            rightTop -> leftTop
            rightCenter -> leftCenter
            rightBottom -> leftBottom
        }
}