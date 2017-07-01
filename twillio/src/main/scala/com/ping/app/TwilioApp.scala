package com.ping.app

import akka.actor.{ActorSystem, Props}
import com.ping.actors.{TwilioConsumerActor, TwilioMessageSenderActor}
import com.ping.kafka.{Consumer, Topics}
import com.ping.logger.PingLogger

object TwilioApp extends App with PingLogger {
  val groupId = "1"
  val servers = "http://localhost:9092"
  val topic = List(Topics.topicMessage)
  val system = ActorSystem("sms-sender")
  val messageSender = system.actorOf(Props[TwilioMessageSenderActor])
  val consumerActor = system.actorOf(TwilioConsumerActor.props(new Consumer(groupId, servers, topic), messageSender))
  println("\n\n\n\n\n"+TwilioConsumerActor.Read+"\n\n\n\n\n")
  consumerActor ! TwilioConsumerActor.Read
  info(s"Twilio message service has been started..... :) ")
}
