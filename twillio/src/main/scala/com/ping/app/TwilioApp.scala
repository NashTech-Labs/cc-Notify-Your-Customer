package com.ping.app

import akka.actor.ActorSystem
import com.ping.actors.{TwilioConsumerActor, TwilioMessageSenderActorFactory}
import com.ping.kafka.{Consumer, Topics}
import com.ping.logger.PingLogger
import com.ping.services.SmsServiceImpl

object TwilioApp extends App with PingLogger {
  val groupId = "1"
  val servers = "http://localhost:9092"
  val topic = List(Topics.topicMessage)
  val system = ActorSystem("sms-sender")
  val messageSender = system.actorOf(TwilioMessageSenderActorFactory.props(SmsServiceImpl.apply(system)))
  val consumerActor = system.actorOf(TwilioConsumerActor.props(new Consumer(groupId, servers, topic), messageSender))
  consumerActor ! TwilioConsumerActor.Read
  info(s"Twilio message service has been started..... :) ")
}
