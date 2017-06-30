package com.knoldus.mail.app

import akka.actor.{ActorSystem, Props}
import com.knoldus.mail.actors.{MailConsumerActor, MailSenderActor}
import com.ping.kafka.Consumer
import com.ping.logger.PingLogger

object MailServiceApp extends App with PingLogger {
  val groupId = ""
  val servers = ""
  val system = ActorSystem("mail-system")
  val mailSender = system.actorOf(Props[MailSenderActor])
  val consumerActor = system.actorOf(MailConsumerActor.props(new Consumer(groupId, servers, Nil), mailSender))
  consumerActor ! MailConsumerActor.Read
}