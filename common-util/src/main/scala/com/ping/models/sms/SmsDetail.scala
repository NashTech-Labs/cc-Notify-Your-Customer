package com.ping.models.sms

case class SmsDetail(to: List[String], body: String, from: Option[String] = None)