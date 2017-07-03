package com.ping.date

import java.sql.Timestamp

import org.joda.time.{DateTime, DateTimeZone}
import org.joda.time.format.DateTimeFormat

trait DateUtil {

  def currentTime: Long = System.currentTimeMillis

  def currentTimestamp: Timestamp = new Timestamp(currentTime)

  private val dateFormats: Seq[String] = Seq("yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
    "yyyy-MM-dd'T'HH:mm:ssZZ", "yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyy-MM-dd'T'HH:mm:ss-SSSS", "yyyy-MM-dd'T'HH:mm:ss+SSSS", "yyyy-MM-dd'T'HH:mmZ")

  def getDateTime(dateString: String): DateTime = {
    DateTimeZone.setDefault(DateTimeZone.UTC)
    def getDateTime(dateFormats: Seq[String], dateString: String): DateTime = {
      try {
        val dtFormat = DateTimeFormat.forPattern(dateFormats.head)
        dtFormat.parseDateTime(dateString)
      } catch {
        case _ if dateFormats.nonEmpty => getDateTime(dateFormats.tail, dateString)
        case ex: Exception =>
          throw new IllegalArgumentException("Unknown date format -> " + dateString, ex)
      }
    }
    getDateTime(dateFormats, dateString)
  }

}

trait UUIDHelper{

  def getRandomUUID: String = java.util.UUID.randomUUID().toString

}

object DateUtil extends DateUtil

object UUIDHelper extends UUIDHelper
