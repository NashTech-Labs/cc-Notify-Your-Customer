package com.knoldus.mail.actors

import akka.actor.Actor
import com.knoldus.mail.services.MailServiceImpl
import com.ping.json.JsonHelper
import com.ping.kafka.MessageFromKafka
import com.ping.logger.PingLogger
import com.ping.models.EmailInfo

class MailSenderActor extends Actor with PingLogger with JsonHelper {

  def receive: PartialFunction[Any, Unit] = {
    case MessageFromKafka(msg:String)=> {
      val emailInfo=parse(msg).extract[EmailInfo]
      MailServiceImpl.sendEmail(emailInfo)
    }
    case _ => error("Error in Mail sender")
  }

}
