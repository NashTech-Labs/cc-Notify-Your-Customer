package com.ping.models

case class SmsDetail(clientId: String, to: List[String], body: String, from: Option[String] = None)