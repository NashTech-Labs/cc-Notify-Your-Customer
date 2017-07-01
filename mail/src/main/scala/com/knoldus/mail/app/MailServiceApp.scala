package com.knoldus.mail.app

import akka.actor.{ActorSystem, Props}
import com.knoldus.mail.actors.{MailConsumerActor, MailSenderActor}
import com.ping.kafka.{Consumer, Topics}
import com.ping.logger.PingLogger

object MailServiceApp extends App with PingLogger {
  val groupId = "1"
  val servers = "http://localhost:9092"
  val topic = List(Topics.topicMail)
  val system = ActorSystem("mail-system")
  val mailSender = system.actorOf(Props[MailSenderActor])
  val consumerActor = system.actorOf(MailConsumerActor.props(new Consumer(groupId, servers, topic), mailSender))
  consumerActor ! MailConsumerActor.Read
  warn("Mail service has been started......")
}