package com.ping.date

import java.sql.Timestamp

trait DateUtil {

  def currentTime: Long = System.currentTimeMillis

  def currentTimestamp: Timestamp = new Timestamp(currentTime)

}

trait UUIDHelper{

  def getRandomUUID: String = java.util.UUID.randomUUID().toString

}

object DateUtil extends DateUtil

object UUIDHelper extends UUIDHelper
