package com.ping.models

case class NotificationDetails(channel: List[String],
                              ids: List[String],
                              data: Map[String, Any],
                              subject: Option[String] = None)