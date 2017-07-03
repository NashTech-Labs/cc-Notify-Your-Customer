package com.ping.json
import java.text.SimpleDateFormat

import com.ping.date.DateUtil
import org.joda.time.DateTime
import org.json4s._
import org.json4s.native.JsonMethods.{parse => jParser}
import org.json4s.native.JsonMethods.{render, pretty => jPretty}
import org.json4s.native.Serialization.{write => jWrite}

import scala.util.Try
import scala.language.implicitConversions



trait JsonHelper{

  val empty = ""
  val javaNull = null

  implicit val formats = new org.json4s.DefaultFormats {
    override def dateFormatter: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
  } ++ org.json4s.ext.JodaTimeSerializers.all ++ List(StringToBigDecimalSerializer)

  case object StringToBigDecimalSerializer extends CustomSerializer[BigDecimal](format => (
    {
      case JInt(value) => BigDecimal(value.longValue)
      case JLong(value) => BigDecimal(value)
      case JNull => javaNull
      case JString(value) => Try(BigDecimal(value.toInt)).getOrElse(BigDecimal(0))
    },
    {
      case d: BigDecimal => JDouble(d.toDouble)
    }
  ))

  protected def write[T <: AnyRef](value: T): String = jWrite(value)

  protected def parse(value: String): JValue = jParser(value)

  protected def pretty(value: String): String = jPretty(render(parse(value)))

  implicit protected def extractOrEmptyString(json: JValue): String = json match {
    case JNothing => empty
    case data     => data.extract[String]
  }

}
