package com.thorebenoit.enamel.kotlin.geometry.alignement

import com.thorebenoit.enamel.kotlin.geometry.allocateNeedsRefactor
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType

interface EOrientation
interface EVerticalOrientation : EOrientation
interface EHorizontalOrientation : EOrientation

sealed class EAlignment(open val o: EOrientation?) {

    object left : EHorizontalOrientation

    object right : EHorizontalOrientation

    object top : EVerticalOrientation

    object bottom : EVerticalOrientation

    object center : EHorizontalOrientation, EVerticalOrientation

    companion object {

        val topLeft: EAlignment = Top(left)
        val topCenter: EAlignment = Top(center)
        val topRight: EAlignment = Top(right)

        val rightTop: EAlignment = Right(top)
        val rightCenter: EAlignment = Right(center)
        val rightBottom: EAlignment = Right(bottom)

        val bottomLeft: EAlignment = Bottom(left)
        val bottomCenter: EAlignment = Bottom(center)
        val bottomRight: EAlignment = Bottom(right)

        val leftTop: EAlignment = Left(top)
        val leftCenter: EAlignment = Left(center)
        val leftBottom: EAlignment = Left(bottom)
        val middle: EAlignment = Center()


        val vertices = listOf(topLeft, topRight, bottomRight, bottomLeft)
        val all = listOf(
            topLeft,
            topCenter,
            topRight,
            rightTop,
            rightCenter,
            rightBottom,
            bottomLeft,
            bottomCenter,
            bottomRight,
            leftTop,
            leftCenter,
            leftBottom,
            middle
        )
    }


    val flipped: EAlignment
        get() = when (this) {
            is Top -> Bottom(o)
            is Bottom -> Top(o)
            is Left -> Right(o)
            is Right -> Left(o)
            is Center -> this
        }
    val spacingSign: EPointType
        get() = allocateNeedsRefactor { // TODO Remove allocation here
            when (this) {
                is Top -> EPointType(0, 1)
                is Bottom -> EPointType(0, -1)
                is Left -> EPointType(1, 0)
                is Right -> EPointType(-1, 0)
                is Center -> EPointType(0, 0)
            }
        }

    val namedPoint: EPointType
        get() = when (this) {
            EAlignment.topLeft -> NamedPoint.topLeft
            EAlignment.topCenter -> NamedPoint.topCenter
            EAlignment.topRight -> NamedPoint.topRight

            EAlignment.bottomLeft -> NamedPoint.bottomLeft
            EAlignment.bottomCenter -> NamedPoint.bottomCenter
            EAlignment.bottomRight -> NamedPoint.bottomRight

            EAlignment.middle -> NamedPoint.center

            EAlignment.leftTop -> NamedPoint.topLeft
            EAlignment.leftCenter -> NamedPoint.middleLeft
            EAlignment.leftBottom -> NamedPoint.bottomLeft

            EAlignment.rightTop -> NamedPoint.topRight
            EAlignment.rightCenter -> NamedPoint.middleRight
            EAlignment.rightBottom -> NamedPoint.bottomRight

            else -> {
                throw Exception("Impossible if using the API properly")
            }
        }

    // -----------------------------------------------------------------

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        return other::class.java == this::class.java && (other as EAlignment).o == o
    }

    override fun hashCode(): Int {
        return o?.hashCode() ?: 0
    }
}

private class Top(override val o: EHorizontalOrientation) : EAlignment(o)
private class Bottom(override val o: EHorizontalOrientation) : EAlignment(o)
private class Center : EAlignment(null)
private class Left(override val o: EVerticalOrientation) : EAlignment(o)
private class Right(override val o: EVerticalOrientation) : EAlignment(o)




