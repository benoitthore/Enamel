package com.thorebenoit.enamel.kotlin.geometry.alignement

import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointImmutable


interface EOrientation
interface EVerticalOrientation : EOrientation
interface EHorizontalOrientation : EOrientation

sealed class EAlignment(open val o: EOrientation?) {


    abstract val flipped: EAlignment

    abstract val spacingSign: EPointImmutable


    companion object {

        object left : EHorizontalOrientation

        object right : EHorizontalOrientation

        object top : EVerticalOrientation

        object bottom : EVerticalOrientation

        object center : EHorizontalOrientation,
            EVerticalOrientation

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
    }


    val namedPoint: EPointImmutable
        get() = when (this) {
            topLeft -> NamedPoint.topLeft
            topCenter -> NamedPoint.topCenter
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

            else -> {
                throw TODO("Refactor with enum so this line is unreachable")
            }
        }
}

private class Top(override val o: EHorizontalOrientation) : EAlignment(o) {
    override val flipped: EAlignment = Bottom(o)
    override val spacingSign: EPointImmutable = EPointImmutable(0, -1)
}

private class Bottom(override val o: EHorizontalOrientation) : EAlignment(o) {
    override val spacingSign: EPointImmutable = EPointImmutable(0, -1)
    override val flipped: EAlignment = Top(o)
}

private class Center : EAlignment(null) {
    override val flipped: EAlignment = this
    override val spacingSign: EPointImmutable = EPointImmutable(0, 0)
}

private class Left(override val o: EVerticalOrientation) : EAlignment(o) {
    override val spacingSign: EPointImmutable = EPointImmutable(1, 0)
    override val flipped: EAlignment = Right(o)
}

private class Right(override val o: EVerticalOrientation) : EAlignment(o) {
    override val spacingSign: EPointImmutable = EPointImmutable(-1, 0)
    override val flipped: EAlignment = Left(o)
}




