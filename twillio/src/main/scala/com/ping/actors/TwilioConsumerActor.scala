package com.ping.actors

import akka.actor.{Actor, ActorRef, Props}
import com.ping.kafka.KafkaConsumerApi
import com.ping.logger.PingLogger

import scala.concurrent.duration.DurationInt

class TwilioConsumerActor(consumer: KafkaConsumerApi, twilioWorker: ActorRef) extends Actor with PingLogger {

  import TwilioConsumerActor._
  import context.dispatcher

  val waitingTime = 1 seconds

  def receive: Receive = {
    case Read =>
      println("we are in consumer now ...")
      val records = consumer.read()
      records foreach { message => twilioWorker ! message }
      if (records.isEmpty) {
        context.system.scheduler.scheduleOnce(waitingTime, self, Read)
      } else {
        self ! Read
      }
  }


}

object TwilioConsumerActor {

  def props(consumer: KafkaConsumerApi, twilioWorker: ActorRef): Props = Props(classOf[TwilioConsumerActor], consumer, twilioWorker)

  case object Read

}