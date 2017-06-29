package com.ping.date

import java.sql.Timestamp

trait DateUtil {

  def currentTime: Long = System.currentTimeMillis

  def currentTimestamp: Timestamp = new Timestamp(currentTime)

}
