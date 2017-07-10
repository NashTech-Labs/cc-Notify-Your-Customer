package com.ping.infrastructure.twillio.api

import com.ping.logger.PingLogger
import com.twilio.Twilio
import com.twilio.`type`.PhoneNumber
import com.twilio.exception.TwilioException
import com.twilio.rest.api.v2010.account.Message

import scala.util.Try


trait TwilioApi extends PingLogger{

  val accountSid: String
  val authToken: String


  def send(fromNumber: String, toNumber: String, text: String): Boolean = Try {
    Twilio.init(accountSid, authToken)
    val message = Message.creator(accountSid, new PhoneNumber(toNumber), new PhoneNumber(fromNumber), text)
    message.create()
    true
  }.toOption match{
    case Some(x) => true
    case None => false
  }

}

//object TwillioApiImpl extends TwilioApi
