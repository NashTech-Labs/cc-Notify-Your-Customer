package com.knoldus.mail.actors

import akka.actor.{Actor, ActorRef, Props}
import com.ping.kafka.KafkaConsumerApi
import com.ping.logger.PingLogger
import scala.concurrent.duration._



class MailConsumerActor(consumer: KafkaConsumerApi, mailSender: ActorRef) extends Actor with PingLogger {

  import MailConsumerActor._
  import context.dispatcher

  val waitingTime: FiniteDuration = 1 seconds

  def receive: Receive = {
    case Read =>
      val records = consumer.read()
      records foreach { message => mailSender ! message }
      if (records.isEmpty) {
        context.system.scheduler.scheduleOnce(waitingTime, self, Read)
      } else {
        self ! Read
      }
  }


}

object MailConsumerActor {

  def props(consumer: KafkaConsumerApi, mailSender: ActorRef): Props = Props(classOf[MailConsumerActor], consumer, mailSender)

  case object Read

}