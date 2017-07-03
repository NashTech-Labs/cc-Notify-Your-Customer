package com.knoldus.mail.app

import akka.actor.ActorSystem
import com.knoldus.mail.actors.{MailConsumerActor, MailSenderActorFactory}
import com.knoldus.mail.services.MailServiceImpl
import com.ping.kafka.{Consumer, Topics}
import com.ping.logger.PingLogger

object MailServiceApp extends App with PingLogger {
  val groupId = "1"
  val servers = "http://localhost:9092"
  val topic = List(Topics.topicMail)
  val system = ActorSystem("mail-system")
  val mailSender = system.actorOf(MailSenderActorFactory.props(MailServiceImpl(system)))
  val consumerActor = system.actorOf(MailConsumerActor.props(new Consumer(groupId, servers, topic), mailSender))
  consumerActor ! MailConsumerActor.Read
  warn("Mail service has been started......")
}