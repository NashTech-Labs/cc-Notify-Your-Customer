package com.knoldus.mail.app

import akka.actor.{ActorRef, ActorSystem, Props}
import com.knoldus.mail.actors.{MailConsumerActor, MailSenderActor}
import com.ping.logger.PingLogger
import com.ping.models.EmailInfo

object MailServiceApp extends App {
  val system = ActorSystem("mail-system")
  val mailSender: ActorRef = system.actorOf(Props[MailSenderActor])
  val consumer: ActorRef = system.actorOf(Props(classOf[MailConsumerActor], mailSender))
  mailSender ! EmailInfo(1, List("himanshu.rajput@knoldus.in"), Nil, Nil, "fga", "cvsdrg")
}