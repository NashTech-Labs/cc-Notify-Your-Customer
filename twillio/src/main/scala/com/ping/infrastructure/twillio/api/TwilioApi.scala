package com.ping.infrastructure.twillio.api

import com.ping.logger.PingLogger
import com.twilio.Twilio
import com.twilio.`type`.PhoneNumber
import com.twilio.exception.TwilioException
import com.twilio.rest.api.v2010.account.Message


trait TwilioApi extends PingLogger{
  val accountSid: String = sys.env.getOrElse("TWILIO_ACCOUNT_SID", "KnoldusTwilio@gmail.com")
  val authToken: String = sys.env.getOrElse("TWILIO_AUTH_TOKEN", "Fake@12ss3d4f6r")
  //println("\n\n\n\n"+accountSid +"\n\n\n\n\n" +authToken)
  def send(fromNumber: String, toNumber: String, text: String): Boolean = try {
    Twilio.init(accountSid, authToken)
    val message = Message.creator(accountSid, new PhoneNumber(toNumber), new PhoneNumber(fromNumber), text)
    message.create()
    true
  } catch {
    case ex: TwilioException => error(s"Failed sending SMS from $fromNumber to $toNumber for: $text", ex)
      false
  }

}

object TwillioApiImpl extends TwilioApi


