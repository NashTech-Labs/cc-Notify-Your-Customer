package com.knoldus.mail.actors

import akka.actor.{Actor, Props}
import com.knoldus.mail.services.MailService
import com.ping.domain.PingEmail
import com.ping.json.JsonHelper
import com.ping.kafka.MessageFromKafka
import com.ping.logger.PingLogger

import scala.concurrent.Future

class MailSenderActor(mailService: MailService) extends Actor with PingLogger with JsonHelper {


  def receive: PartialFunction[Any, Unit] = {
    case MessageFromKafka(msg: String) => {
      info(s"Got message from kafka... $msg")
      parse(msg).extractOpt[PingEmail] match {
        case Some(mail) =>
          info(s"Processing message : $mail")
          mailService.sendEmail(mail)
        case None =>
          error(s"Unable to parse message : $msg")
          Future.successful(Some(0))
      }
    }
    case _ => error("Error in Mail sender")
  }

}


object MailSenderActorFactory {
  def props(mailService: MailService): Props = Props(classOf[MailSenderActor], mailService)
}
