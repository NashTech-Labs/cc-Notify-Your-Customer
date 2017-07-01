package com.ping.actors

import akka.actor.Actor
import akka.stream.ActorMaterializer
import com.ping.json.JsonHelper
import com.ping.kafka.MessageFromKafka
import com.ping.logger.PingLogger
import com.ping.models.SmsDetail
import com.ping.services.SmsServiceImpl


class TwilioMessageSenderActor extends Actor with PingLogger with JsonHelper {

  implicit val actorSystem = context.system
  implicit val materializer = ActorMaterializer()


  def receive: Receive = {
    case MessageFromKafka(message: String) => {
      println("\n\n\n\n here it comes in main actor ....")
      val smsInfo = parse(message).extract[SmsDetail]
      println(smsInfo)
      SmsServiceImpl.send(smsInfo)
    }
    case otherMsg => info(s"Oops.! Could not understand message.! $otherMsg")

  }
}

