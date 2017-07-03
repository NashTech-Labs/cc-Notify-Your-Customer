package com.ping.actors

import akka.actor.{Props, ActorSystem, Actor}
import akka.stream.ActorMaterializer
import com.ping.domain.TwilioMessage
import com.ping.json.JsonHelper
import com.ping.kafka.{Consumer, MessageFromKafka}
import com.ping.logger.PingLogger
import com.ping.services.{SmsService}


class TwilioMessageSenderActor(smsService: SmsService) extends Actor with PingLogger with JsonHelper {

  implicit val actorSystem = context.system
  implicit val materializer = ActorMaterializer()

  def receive: Receive = {
    case MessageFromKafka(message: String) => {
      val smsInfo = parse(message).extract[TwilioMessage]
      smsService.send(smsInfo)
    }
    case otherMsg => info(s"Oops.! Could not understand message.! $otherMsg")
  }

}

object TwilioMessageSenderActorFactory{

  def props(smsService: SmsService) = Props(classOf[TwilioMessageSenderActor], smsService)
}

