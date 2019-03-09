package com.thorebenoit.enamel.kotlin.geometry.alignement

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.thorebenoit.enamel.kotlin.geometry.allocate
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType

interface EOrientation
interface EVerticalOrientation : EOrientation
interface EHorizontalOrientation : EOrientation


class EAlignmentSerializer : JsonSerializer<EAlignment>() {
    override fun serialize(value: EAlignment, gen: JsonGenerator, serializers: SerializerProvider?) {
        gen.writeObject(value.namedPoint)
    }
}

class EAlignmentDeserializer : JsonDeserializer<EAlignment>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): EAlignment {
        val point = p.readValuesAs(EPointType::class.java)
        return EAlignment.fromNamedPoint(point.next())
    }

}

@JsonSerialize(using = EAlignmentSerializer::class)
@JsonDeserialize(using = EAlignmentDeserializer::class)
sealed class EAlignment(open val o: EOrientation?) {


    companion object {

        fun fromNamedPoint(namedPoint: EPointType): EAlignment {
//            TODO("9 NamedPoint for 12 EAlignement")
            return when (namedPoint) {
                NamedPoint.topLeft -> EAlignment.topLeft
                NamedPoint.topCenter -> EAlignment.topCenter
                NamedPoint.topRight -> EAlignment.topRight
                NamedPoint.bottomLeft -> EAlignment.bottomLeft
                NamedPoint.bottomCenter -> EAlignment.bottomCenter
                NamedPoint.bottomRight -> EAlignment.bottomRight
                NamedPoint.center -> EAlignment.middle
                NamedPoint.topLeft -> EAlignment.topLeft
                NamedPoint.middleLeft -> EAlignment.leftCenter
                NamedPoint.bottomLeft -> EAlignment.bottomLeft
                NamedPoint.topRight -> EAlignment.topRight
                NamedPoint.middleRight -> EAlignment.rightCenter
                NamedPoint.bottomRight -> EAlignment.bottomRight

                else -> {
                    throw Exception("Impossible if using the API properly")
                }
            }
        }

        private object left : EHorizontalOrientation

        private object right : EHorizontalOrientation

        private object top : EVerticalOrientation

        private object bottom : EVerticalOrientation

        private object center : EHorizontalOrientation, EVerticalOrientation

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
        get() = allocate {
            // TODO Remove allocation here
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

    val isVertical get() = this is Top || this is Bottom
    val isHorizontal get() = this is Left || this is Right


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

class Top(override val o: EHorizontalOrientation) : EAlignment(o)
class Bottom(override val o: EHorizontalOrientation) : EAlignment(o)
class Center : EAlignment(null)
class Left(override val o: EVerticalOrientation) : EAlignment(o)
class Right(override val o: EVerticalOrientation) : EAlignment(o)




