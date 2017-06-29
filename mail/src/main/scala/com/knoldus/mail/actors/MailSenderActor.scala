package com.knoldus.mail.actors
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.actor.Actor.Receive
import com.knoldus.mail.services.EmailApiImpl
import com.ping.logger.PingLogger
import com.ping.models.EmailInfo

class MailSenderActor extends Actor with PingLogger{

  def receive: PartialFunction[Any, Unit] = {
    case email:EmailInfo =>

      EmailApiImpl.send(email)
    case msg =>  error("Error in Mail sender")
  }

}
