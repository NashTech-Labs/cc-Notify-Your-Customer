package com.knoldus.mail.actors

import akka.actor.{Actor, ActorRef}
import com.knoldus.mail.services.MailService
import com.ping.logger.PingLogger
import com.ping.models.EmailInfo


class MailConsumerActor(mailsender: ActorRef, mailService: MailService) extends Actor with PingLogger {
  override def receive: Receive = {
    case emailInfo: EmailInfo =>
      mailService.send(emailInfo)
    case _ => error("Invalid message consumed")
  }
}
