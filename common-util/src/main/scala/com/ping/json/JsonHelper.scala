package com.ping.json

import org.json4s.native.JsonMethods.{render, parse => jParser, pretty => jPretty}
import org.json4s.native.Serialization.{write => jWrite}
import org.json4s.{CustomSerializer, JDouble, JInt, JLong, JNothing, JNull, JString, JValue}

import scala.util.Try


//scalastyle:off
trait JsonHelper {

  val emptyStr = ""
  val jNull = null

  implicit val formats = new org.json4s.DefaultFormats { List(StringToBigDecimalSerializer) }

  case object StringToBigDecimalSerializer extends CustomSerializer[BigDecimal](format => (
    {
      case JInt(value) => BigDecimal(value.longValue)
      case JLong(value) => BigDecimal(value)
      case JNull => jNull
      case JString(value) => Try(BigDecimal(value.toInt)).getOrElse(BigDecimal(0))
    },
    {
      case d: BigDecimal => JDouble(d.toDouble)
    }
  ))

  protected def write[T <: AnyRef](value: T): String = jWrite(value)

  protected def parse(value: String): JValue = jParser(value)

  implicit protected def extractOrEmptyString(json: JValue): String = json match {
    case JNothing => emptyStr
    case data     => data.extract[String]
  }

}
//scalastyle:on
