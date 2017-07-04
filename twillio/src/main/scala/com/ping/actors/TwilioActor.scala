package com.ping.actors

import akka.actor.{Props, ActorSystem, Actor}
import akka.stream.ActorMaterializer
import com.ping.domain.TwilioMessage
import com.ping.json.JsonHelper
import com.ping.kafka.{Consumer, MessageFromKafka}
import com.ping.logger.PingLogger
import com.ping.services.SmsService

import scala.concurrent.Future


class TwilioMessageSenderActor(smsService: SmsService) extends Actor with PingLogger with JsonHelper {

  implicit val actorSystem = context.system
  implicit val materializer = ActorMaterializer()


  def receive: Receive = {
    case MessageFromKafka(message: String) => {
      info(s"Got message from sms topic...: ${message}")
      parse(message).extractOpt[TwilioMessage] match {
        case Some(msg) =>
          smsService.send(msg)
        case None =>
          error(s"Error found while parsing message: ${message}")
          Future.successful(false)
      }
    }
    case otherMsg => info(s"Oops.! Could not understand message.! $otherMsg")
  }

}

object TwilioMessageSenderActorFactory{

  def props(smsService: SmsService) = Props(classOf[TwilioMessageSenderActor], smsService)
}

