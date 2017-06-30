package com.knoldus.mail.actors

import akka.actor.Actor
import com.knoldus.mail.services.MailService
import com.ping.logger.PingLogger
import com.ping.models.EmailInfo

class MailSenderActor(mailService: MailService, emailInfo: EmailInfo) extends Actor with PingLogger {

  def receive: PartialFunction[Any, Unit] = {
    case email: EmailInfo => {
      mailService.sendEmail(emailInfo)
    }
    case _ => error("Error in Mail sender")
  }

}
