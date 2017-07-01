package com.ping.models

case class SmsDetail(clientId: Int, to: List[String], body: String, from: Option[String] = None)