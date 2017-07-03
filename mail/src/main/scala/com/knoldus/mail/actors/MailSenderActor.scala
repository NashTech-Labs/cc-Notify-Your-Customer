package com.knoldus.mail.actors

import akka.actor.{Actor, Props}
import com.knoldus.mail.services.MailService
import com.ping.domain.PingEmail
import com.ping.json.JsonHelper
import com.ping.kafka.MessageFromKafka
import com.ping.logger.PingLogger

class MailSenderActor(mailService: MailService) extends Actor with PingLogger with JsonHelper {


  def receive: PartialFunction[Any, Unit] = {
    case MessageFromKafka(msg: String) => {
      val emailInfo = parse(msg).extract[PingEmail]
      mailService.sendEmail(emailInfo)
    }
    case _ => error("Error in Mail sender")
  }

}


object MailSenderActorFactory {

  def props(mailService: MailService): Props = Props(classOf[MailSenderActor], mailService)

}
