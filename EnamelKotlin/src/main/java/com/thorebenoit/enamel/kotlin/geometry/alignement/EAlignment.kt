package com.thorebenoit.enamel.kotlin.geometry.alignement

import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType

/* UI TEST
PlaygroundApplet.start(800, 800) {

        onDraw {
            background(255)
            noFill()
            stroke(red)
            val rect = eframe.inset(100).draw()
            EAlignment.all.forEach {
                rect.rectAlignedOutside(it, ESizeType.square(20), spacing = 10).draw()
            }

        }

    }
 */
private val spacingSignTop = EPointType(0, 1)
private val spacingSignBottom = EPointType(0, -1)
private val spacingSignLeft = EPointType(1, 0)
private val spacingSignRight = EPointType(-1, 0)
private val spacingSignCenter = EPointType(0, 0)

enum class EAlignment {
    topLeft,
    topCenter,
    topRight,
    bottomLeft,
    bottomCenter,
    bottomRight,
    middle,
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
            middle,
            leftTop,
            leftCenter,
            leftBottom,
            rightTop,
            rightCenter,
            rightBottom
        )
    }


    val namedPoint: EPointType
        get() = when (this) {
            topCenter -> NamedPoint.topCenter
            topLeft -> NamedPoint.topLeft
            topRight -> NamedPoint.topRight
            bottomLeft -> NamedPoint.bottomLeft
            bottomCenter -> NamedPoint.bottomCenter
            bottomRight -> NamedPoint.bottomRight
            middle -> NamedPoint.center
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
            middle -> true
            else -> {
                false
            }
        }

    val isHorizontal : Boolean
        get() = this == middle || !isVertical

    val spacingSign: EPointType
        get() = when (this) {
            topLeft -> spacingSignTop
            topCenter -> spacingSignTop
            topRight -> spacingSignTop
            bottomLeft -> spacingSignBottom
            bottomCenter -> spacingSignBottom
            bottomRight -> spacingSignBottom
            middle -> spacingSignCenter
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
            middle -> middle
            leftTop -> rightTop
            leftCenter -> rightCenter
            leftBottom -> rightBottom
            rightTop -> leftTop
            rightCenter -> leftCenter
            rightBottom -> leftBottom
        }
}