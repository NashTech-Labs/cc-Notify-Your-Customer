package com.ping.actors

import akka.actor.{Props, ActorSystem, Actor}
import akka.stream.ActorMaterializer
import com.ping.domain.TwilioMessage
import com.ping.json.JsonHelper
import com.ping.kafka.{Consumer, MessageFromKafka}
import com.ping.logger.PingLogger
import com.ping.services.{SmsService}

import scala.util.control.NonFatal


class TwilioMessageSenderActor(smsService: SmsService) extends Actor with PingLogger with JsonHelper {

  implicit val actorSystem = context.system
  implicit val materializer = ActorMaterializer()

  def receive: Receive = {
    case message: String => {
      info(s"message received........$message")
      val smsInfo = try{
        val msg = parse(write(message)).extract[TwilioMessage]
        info(s"Got message to deliver.........${msg}")
        smsService.send(msg)
      }
      catch{
        case NonFatal(ex) =>
          error(s"Error found............${ex.printStackTrace()}")
      }

    }
    case otherMsg => info(s"Oops.! Could not understand message.! $otherMsg")
  }

}

object TwilioMessageSenderActorFactory{

  def props(smsService: SmsService) = Props(classOf[TwilioMessageSenderActor], smsService)
}

